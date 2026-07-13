package com.pascal.xpense.ui.screen.addtransaction.state

data class AddTransactionUIState(
    val title: String = "",
    val amount: String = "",
    val date: String = "",
    val category: String = "Shopping",
    val note: String = "",
    val amountError: Boolean = false,
    val titleError: Boolean = false,
    val dateError: Boolean = false,
    val isSaving: Boolean = false
)
