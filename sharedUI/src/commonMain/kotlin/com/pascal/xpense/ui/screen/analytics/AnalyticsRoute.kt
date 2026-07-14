package com.pascal.xpense.ui.screen.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.screen.analytics.state.LocalAnalyticsEvent
import org.koin.compose.koinInject

@Composable
fun AnalyticsRoute(
    viewModel: AnalyticsViewModel = koinInject<AnalyticsViewModel>()
) {
    val event = LocalAnalyticsEvent.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalAnalyticsEvent provides event.copy(
            onRefresh = { viewModel.loadAnalytics() }
        )
    ) {
        AnalyticsScreen(uiState = uiState)
    }
}
