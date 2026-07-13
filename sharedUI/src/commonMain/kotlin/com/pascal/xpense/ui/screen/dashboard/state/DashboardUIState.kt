package com.pascal.xpense.ui.screen.dashboard.state

import com.pascal.xpense.data.local.entity.TransactionEntity

data class DashboardUIState(
    val isLoading: Boolean = true,
    val error: Pair<Boolean, String> = false to "",
    val totalExpense: Double = 0.0,
    val transactions: List<TransactionEntity> = emptyList(),
    val groupedTransactions: Map<String, List<TransactionEntity>> = emptyMap()
)
