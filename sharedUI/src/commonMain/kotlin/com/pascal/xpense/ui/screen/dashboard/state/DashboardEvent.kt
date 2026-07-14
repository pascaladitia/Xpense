package com.pascal.xpense.ui.screen.dashboard.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalDashboardEvent = compositionLocalOf { DashboardEvent() }

@Stable
data class DashboardEvent(
    val onAddTransaction: () -> Unit = {},
    val onDeleteTransaction: (Long) -> Unit = {},
    val onSearchTransaction: (String) -> Unit = {}
)
