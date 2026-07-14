package com.pascal.xpense.ui.screen.addtransaction.state

data class AddTransactionUIState(
    val title: String = "",
    val amount: String = "",
    val date: String = "",
    val type: String = "EXPENSE",
    val category: String = "Food",
    val attachmentPath: String? = null,
    val attachmentBytes: ByteArray? = null,
    val amountError: Boolean = false,
    val titleError: Boolean = false,
    val dateError: Boolean = false,
    val isSaving: Boolean = false
)
