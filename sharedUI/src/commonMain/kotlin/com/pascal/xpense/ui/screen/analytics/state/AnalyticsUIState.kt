package com.pascal.xpense.ui.screen.analytics.state

import com.pascal.xpense.domain.model.CategoryBreakdown

data class AnalyticsUIState(
    val isLoading: Boolean = true,
    val totalExpense: Double = 0.0,
    val lastMonthExpense: Double = 0.0,
    val categoryBreakdown: List<CategoryBreakdown> = emptyList(),
    val topCategory: CategoryBreakdown? = null,
    val savingPercent: Float = 0f,
    val isSavingMore: Boolean = true
)
