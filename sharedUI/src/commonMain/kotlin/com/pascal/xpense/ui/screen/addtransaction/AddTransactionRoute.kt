package com.pascal.xpense.ui.screen.addtransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.screen.addtransaction.state.AddTransactionEvent
import com.pascal.xpense.ui.screen.addtransaction.state.LocalAddTransactionEvent
import org.koin.compose.koinInject

@Composable
fun AddTransactionRoute(
    viewModel: AddTransactionViewModel = koinInject<AddTransactionViewModel>(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalAddTransactionEvent provides AddTransactionEvent(
            onNavigateBack = onNavigateBack
        )
    ) {
        AddTransactionScreen(
            uiState = uiState,
            onTitleChange = { viewModel.updateTitle(it) },
            onAmountChange = { viewModel.updateAmount(it) },
            onDateChange = { viewModel.updateDate(it) },
            onCategoryChange = { viewModel.updateCategory(it) },
            onSave = { viewModel.save(onNavigateBack) },
            onCancel = onNavigateBack
        )
    }
}
