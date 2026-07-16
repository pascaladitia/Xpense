package com.pascal.xpense.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.domain.mapper.formatConfirmation
import com.pascal.xpense.domain.mapper.isKnownTransactionType
import com.pascal.xpense.domain.mapper.normalizeCategory
import com.pascal.xpense.domain.mapper.normalizeTransactionType
import com.pascal.xpense.domain.mapper.parseAITransaction
import com.pascal.xpense.domain.mapper.resolveDate
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.domain.usecase.remote.RemoteUseCase
import com.pascal.xpense.ui.screen.chat.state.ChatMessage
import com.pascal.xpense.ui.screen.chat.state.ChatUIState
import com.pascal.xpense.ui.screen.chat.state.PendingImage
import com.pascal.xpense.utils.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock

class ChatViewModel(
    private val remoteUseCase: RemoteUseCase,
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUIState())
    val uiState: StateFlow<ChatUIState> = _uiState.asStateFlow()

    private val history = mutableListOf<ChatTurn>()

    init {
        history.add(ChatTurn(Constant.AI_ROLE_SYSTEM, Constant.SYSTEM_PROMPT))
        _uiState.update {
            it.copy(
                messages = listOf(
                    ChatMessage(
                        id = generateId(),
                        role = Constant.AI_ROLE_ASSISTANT,
                        text = Constant.AI_WELCOME_MESSAGE
                    )
                )
            )
        }
    }

    fun updateInput(value: String) {
        _uiState.update { it.copy(input = value) }
    }

    fun setImage(bytes: ByteArray, name: String) {
        _uiState.update { it.copy(pendingImage = PendingImage(bytes, name)) }
    }

    fun clearImage() {
        _uiState.update { it.copy(pendingImage = null) }
    }

    fun send() {
        val state = _uiState.value
        val text = state.input.trim()
        if (text.isBlank() && state.pendingImage == null) return

        val userText = text.ifBlank { Constant.AI_IMAGE_FALLBACK_TEXT }
        val imageDataUrl = state.pendingImage?.let { toDataUrl(it.bytes) }

        history.add(ChatTurn(Constant.AI_ROLE_USER, userText, imageDataUrl))

        _uiState.update {
            it.copy(
                messages = it.messages + ChatMessage(
                    id = generateId(),
                    role = Constant.AI_ROLE_USER,
                    text = userText,
                    imageBytes = state.pendingImage?.bytes
                ),
                input = "",
                pendingImage = null,
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            handleResponse()
        }
    }

    private suspend fun handleResponse() {
        try {
            val reply = remoteUseCase.chatAI(history)
            history.add(ChatTurn(Constant.AI_ROLE_ASSISTANT, reply))

            val displayText = processReply(reply)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    messages = it.messages + ChatMessage(
                        id = generateId(),
                        role = Constant.AI_ROLE_ASSISTANT,
                        text = displayText,
                        raw = reply
                    )
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(isLoading = false, error = e.message ?: Constant.UNKNOWN_ERROR)
            }
        }
    }

    private suspend fun processReply(reply: String): String {
        val txn = parseAITransaction(reply)
        if (txn == null || txn.amount <= 0 || !isKnownTransactionType(txn.type)) return reply

        val type = normalizeTransactionType(txn.type)
        val category = normalizeCategory(type, txn.category)
        val date = resolveDate(txn.date)

        localUseCase.saveTransaction(
            TransactionEntity(
                title = txn.title.ifBlank { Constant.DEFAULT_TITLE },
                amount = txn.amount,
                date = date,
                type = type,
                category = category
            )
        )

        return formatConfirmation(type, txn.title, txn.amount, category, date)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun toDataUrl(bytes: ByteArray): String {
        val encoded = Base64.encode(bytes)
        return "${Constant.DATA_IMAGE_JPEG_PREFIX}$encoded"
    }

    private fun generateId(): String = "${Clock.System.now()}-${(0..9999).random()}"
}
