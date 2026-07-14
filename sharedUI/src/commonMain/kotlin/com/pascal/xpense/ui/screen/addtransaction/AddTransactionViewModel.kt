package com.pascal.xpense.ui.screen.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.ui.screen.addtransaction.state.AddTransactionUIState
import com.pascal.xpense.utils.currentTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AddTransactionViewModel(
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUIState())
    val uiState: StateFlow<AddTransactionUIState> = _uiState.asStateFlow()

    init {
        val now = Instant
            .fromEpochMilliseconds(currentTimeMillis())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val today = "${now.year}-${now.monthNumber.toString().padStart(2, '0')}-${now.dayOfMonth.toString().padStart(2, '0')}"
        _uiState.update { it.copy(date = today) }
    }

    fun updateTitle(value: String) {
        _uiState.update { it.copy(title = value, titleError = false) }
    }

    fun updateAmount(value: String) {
        if (value.all { it.isDigit() || it == '.' }) {
            _uiState.update { it.copy(amount = value, amountError = false) }
        }
    }

    fun updateDate(value: String) {
        _uiState.update { it.copy(date = value, dateError = false) }
    }

    fun updateType(value: String) {
        val defaultCategory = if (value == "EXPENSE") "Food" else "Salary"
        _uiState.update { it.copy(type = value, category = defaultCategory) }
    }

    fun updateCategory(value: String) {
        _uiState.update { it.copy(category = value) }
    }

    fun setAttachment(path: String?, bytes: ByteArray? = null) {
        _uiState.update { it.copy(attachmentPath = path, attachmentBytes = bytes) }
    }

    fun clearAttachment() {
        _uiState.update { it.copy(attachmentPath = null, attachmentBytes = null) }
    }

    fun save(onSuccess: () -> Unit) {
        val state = _uiState.value

        val amountValue = state.amount.toDoubleOrNull()
        val hasAmountError = amountValue == null || amountValue <= 0
        val hasTitleError = state.title.isBlank()
        val hasDateError = state.date.isBlank()

        if (hasAmountError || hasTitleError || hasDateError) {
            _uiState.update {
                it.copy(
                    amountError = hasAmountError,
                    titleError = hasTitleError,
                    dateError = hasDateError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            localUseCase.saveTransaction(
                TransactionEntity(
                    title = state.title.trim(),
                    amount = amountValue,
                    date = state.date,
                    type = state.type,
                    category = state.category,
                    attachmentPath = state.attachmentPath
                )
            )

            resetState()
            onSuccess()
        }
    }

    private fun resetState() {
        _uiState.value = AddTransactionUIState()
    }
}
