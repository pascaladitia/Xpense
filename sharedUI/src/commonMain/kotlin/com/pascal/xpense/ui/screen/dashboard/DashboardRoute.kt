package com.pascal.xpense.ui.screen.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.screen.dashboard.state.LocalDashboardEvent
import org.koin.compose.koinInject

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = koinInject<DashboardViewModel>(),
    onAddTransaction: () -> Unit
) {
    val event = LocalDashboardEvent.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalDashboardEvent provides event.copy(
            onAddTransaction = onAddTransaction,
            onDeleteTransaction = { viewModel.deleteTransaction(it) }
        )
    ) {
        DashboardScreen(uiState = uiState)
    }
}
