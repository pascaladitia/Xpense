package com.pascal.xpense.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.data.remote.api.AIService
import com.pascal.xpense.data.remote.api.ChatPrompt
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.data.remote.mapper.normalizeCategory
import com.pascal.xpense.data.remote.mapper.normalizeTransactionType
import com.pascal.xpense.data.remote.mapper.parseAITransaction
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.ui.screen.chat.state.ChatMessage
import com.pascal.xpense.ui.screen.chat.state.ChatUIState
import com.pascal.xpense.ui.screen.chat.state.PendingImage
import com.pascal.xpense.utils.currentTimeMillis
import com.pascal.xpense.utils.formatAmount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock

private val DATE_PATTERN = Regex("^\\d{4}-\\d{2}-\\d{2}\$")

class ChatViewModel(
    private val aiService: AIService,
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUIState())
    val uiState: StateFlow<ChatUIState> = _uiState.asStateFlow()

    private val history = mutableListOf<ChatTurn>()

    init {
        history.add(ChatTurn("system", ChatPrompt.system))
        _uiState.update {
            it.copy(
                messages = listOf(
                    ChatMessage(
                        id = nextId(),
                        role = "assistant",
                        text = "Hello! I'm your finance assistant. Tell me about your income or expenses, or send a receipt photo, and I'll record it to the database."
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

        val imageDataUrl = state.pendingImage?.let { toDataUrl(it.bytes) }
        val userText = text.ifBlank { "Look at this image and record the transaction if any." }

        history.add(ChatTurn("user", userText, imageDataUrl))

        val userMessage = ChatMessage(
            id = nextId(),
            role = "user",
            text = userText,
            imageBytes = state.pendingImage?.bytes
        )

        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                input = "",
                pendingImage = null,
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                val reply = aiService.chat(history)
                history.add(ChatTurn("assistant", reply))

                val txn = parseAITransaction(reply)
                val message = if (txn != null && txn.amount > 0 && isKnownType(txn.type)) {
                    val type = normalizeTransactionType(txn.type)
                    val category = normalizeCategory(type, txn.category)
                    val date = resolveDate(txn.date)
                    localUseCase.saveTransaction(
                        TransactionEntity(
                            title = txn.title.ifBlank { "Transaction" },
                            amount = txn.amount,
                            date = date,
                            type = type,
                            category = category
                        )
                    )
                    confirmText(type, txn.title, txn.amount, category, date)
                } else {
                    reply
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messages = it.messages + ChatMessage(
                            id = nextId(),
                            role = "assistant",
                            text = message,
                            raw = reply
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Something went wrong")
                }
            }
        }
    }

    private fun isKnownType(raw: String): Boolean {
        val t = normalizeTransactionType(raw)
        return t == "INCOME" || t == "EXPENSE"
    }

    private fun resolveDate(raw: String?): String {
        return if (!raw.isNullOrBlank() && raw.matches(DATE_PATTERN)) raw else today()
    }

    private fun today(): String {
        val now = Instant
            .fromEpochMilliseconds(currentTimeMillis())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return "${now.year}-${now.month.number.toString().padStart(2, '0')}-${now.day.toString().padStart(2, '0')}"
    }

    private fun confirmText(type: String, title: String, amount: Double, category: String, date: String): String {
        val kind = if (type == "INCOME") "Income" else "Expense"
        return "✅ $kind recorded:\n${title.ifBlank { "Transaction" }} • $${formatAmount(amount)} • $category • $date"
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun toDataUrl(bytes: ByteArray): String {
        val encoded = Base64.encode(bytes)
        return "data:image/jpeg;base64,$encoded"
    }

    private fun nextId(): String = "${Clock.System.now()}-${(0..9999).random()}"
}
