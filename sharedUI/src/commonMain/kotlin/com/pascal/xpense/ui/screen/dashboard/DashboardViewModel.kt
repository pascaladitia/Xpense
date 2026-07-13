package com.pascal.xpense.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.ui.screen.dashboard.state.DashboardUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUIState())
    val uiState: StateFlow<DashboardUIState> = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            combine(
                localUseCase.getAllTransactions(),
                localUseCase.observeTotalIncome(),
                localUseCase.observeTotalExpense()
            ) { transactions, income, expense ->
                val balance = income - expense
                DashboardUIState(
                    isLoading = false,
                    totalBalance = balance,
                    totalIncome = income,
                    totalExpense = expense,
                    transactions = transactions,
                    groupedTransactions = transactions.groupBy { it.date }
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun refresh() {
        observeData()
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            val current = _uiState.value.transactions.find { it.id == id } ?: return@launch
            localUseCase.deleteTransaction(current)
        }
    }

    fun resetError() {
        _uiState.update { it.copy(error = false to "") }
    }
}
