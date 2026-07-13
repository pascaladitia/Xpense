package com.pascal.xpense.ui.screen.addtransaction.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalAddTransactionEvent = compositionLocalOf { AddTransactionEvent() }

@Stable
data class AddTransactionEvent(
    val onNavigateBack: () -> Unit = {}
)
