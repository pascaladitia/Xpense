package com.pascal.xpense.ui.screen.addtransaction.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalAddTransactionEvent = compositionLocalOf { AddTransactionEvent() }

@Stable
data class AddTransactionEvent(
    val onTitleChange: (String) -> Unit = {},
    val onAmountChange: (String) -> Unit = {},
    val onDateChange: (String) -> Unit = {},
    val onTypeChange: (String) -> Unit = {},
    val onCategoryChange: (String) -> Unit = {},
    val onAttachmentChange: (String?) -> Unit = {},
    val onSave: () -> Unit = {},
    val onCancel: () -> Unit = {},
    val onDateClick: () -> Unit = {},
    val onAttachmentClick: () -> Unit = {},
    val onClearAttachment: () -> Unit = {}
)
