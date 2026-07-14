package com.pascal.xpense.ui.screen.dashboard

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
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
import kotlin.collections.copy

class DashboardViewModel(
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUIState())
    val uiState: StateFlow<DashboardUIState> = _uiState.asStateFlow()

    private var allTransactions: List<TransactionEntity> = emptyList()

    init {
        observeData()
    }

    fun setTransition(
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        _uiState.update {
            it.copy(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
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
                allTransactions = transactions
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

    fun searchTransaction(query: String) {
        val filtered = if (query.isBlank()) {
            allTransactions
        } else {
            val normalized = query.trim().lowercase()
            allTransactions.filter { t ->
                t.title.lowercase().contains(normalized) ||
                t.category.lowercase().contains(normalized) ||
                t.type.lowercase().contains(normalized) ||
                t.amount.toString().contains(normalized) ||
                t.date.lowercase().contains(normalized)
            }
        }
        _uiState.update {
            it.copy(
                transactions = filtered,
                groupedTransactions = filtered.groupBy { it.date }
            )
        }
    }

    fun resetError() {
        _uiState.update { it.copy(error = false to "") }
    }
}
