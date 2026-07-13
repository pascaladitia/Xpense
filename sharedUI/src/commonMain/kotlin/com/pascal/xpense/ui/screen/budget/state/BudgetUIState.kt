package com.pascal.xpense.ui.screen.budget.state

data class BudgetUIState(
    val isLoading: Boolean = false,
    val budgets: Map<String, Double> = emptyMap(),
    val totalBudget: Double = 1000.0,
    val totalSpent: Double = 0.0
)
