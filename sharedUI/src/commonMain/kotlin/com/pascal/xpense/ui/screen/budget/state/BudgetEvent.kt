package com.pascal.xpense.ui.screen.budget.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalBudgetEvent = compositionLocalOf { BudgetEvent() }

@Stable
data class BudgetEvent(
    val onSetBudget: () -> Unit = {}
)
