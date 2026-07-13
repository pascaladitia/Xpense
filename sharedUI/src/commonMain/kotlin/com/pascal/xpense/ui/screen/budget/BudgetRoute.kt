package com.pascal.xpense.ui.screen.budget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.screen.budget.state.LocalBudgetEvent
import org.koin.compose.koinInject

@Composable
fun BudgetRoute(
    viewModel: BudgetViewModel = koinInject<BudgetViewModel>()
) {
    val event = LocalBudgetEvent.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalBudgetEvent provides event.copy()
    ) {
        BudgetScreen(uiState = uiState)
    }
}
