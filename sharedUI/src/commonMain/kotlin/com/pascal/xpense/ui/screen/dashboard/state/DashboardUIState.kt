package com.pascal.xpense.ui.screen.dashboard.state

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import com.pascal.xpense.data.local.entity.TransactionEntity

data class DashboardUIState(
    val isLoading: Boolean = true,
    val error: Pair<Boolean, String> = false to "",
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val transactions: List<TransactionEntity> = emptyList(),
    val groupedTransactions: Map<String, List<TransactionEntity>> = emptyMap(),


    val sharedTransitionScope: SharedTransitionScope? = null,
    val animatedVisibilityScope: AnimatedVisibilityScope? = null
)
