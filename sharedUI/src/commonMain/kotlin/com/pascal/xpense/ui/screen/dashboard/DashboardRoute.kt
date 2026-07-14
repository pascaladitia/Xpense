package com.pascal.xpense.ui.screen.dashboard

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.screen.dashboard.state.LocalDashboardEvent
import org.koin.compose.koinInject

@Composable
fun DashboardRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: DashboardViewModel = koinInject<DashboardViewModel>(),
    onAddTransaction: () -> Unit
) {
    val event = LocalDashboardEvent.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setTransition(sharedTransitionScope, animatedVisibilityScope)
    }

    CompositionLocalProvider(
        LocalDashboardEvent provides event.copy(
            onAddTransaction = onAddTransaction,
            onDeleteTransaction = viewModel::deleteTransaction,
            onSearchTransaction = viewModel::searchTransaction
        )
    ) {
        DashboardScreen(uiState = uiState)
    }
}
