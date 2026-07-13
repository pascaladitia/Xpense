package com.pascal.xpense.ui.screen.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.ui.screen.budget.state.BudgetUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetUIState())
    val uiState: StateFlow<BudgetUIState> = _uiState.asStateFlow()

    init {
        observeBudgets()
    }

    private fun observeBudgets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            combine(
                localUseCase.observeExpenseByCategory(),
                localUseCase.observeTotalExpense()
            ) { categories, totalExpense ->
                val budgets = categories.associate { it.category to it.total }
                BudgetUIState(
                    isLoading = false,
                    budgets = budgets,
                    totalSpent = totalExpense
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
