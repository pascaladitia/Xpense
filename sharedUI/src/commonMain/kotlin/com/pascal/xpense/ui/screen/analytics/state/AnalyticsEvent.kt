package com.pascal.xpense.ui.screen.analytics.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalAnalyticsEvent = compositionLocalOf { AnalyticsEvent() }

@Stable
data class AnalyticsEvent(
    val onRefresh: () -> Unit = {}
)
