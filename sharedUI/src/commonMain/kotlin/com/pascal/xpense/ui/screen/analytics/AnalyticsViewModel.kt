package com.pascal.xpense.ui.screen.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.ui.screen.analytics.state.AnalyticsUIState
import com.pascal.xpense.utils.currentTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AnalyticsViewModel(
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUIState())
    val uiState: StateFlow<AnalyticsUIState> = _uiState.asStateFlow()

    init {
        loadAnalytics()
    }

    fun loadAnalytics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val now = Instant
                .fromEpochMilliseconds(currentTimeMillis())
                .toLocalDateTime(TimeZone.currentSystemDefault())

            val currentYearMonth = "${now.year}-${now.monthNumber.toString().padStart(2, '0')}"

            val lastMonth = now.monthNumber - 1
            val lastYear = if (lastMonth == 0) now.year - 1 else now.year
            val lastMonthNumber = if (lastMonth == 0) 12 else lastMonth
            val lastYearMonth = "$lastYear-${lastMonthNumber.toString().padStart(2, '0')}"

            combine(
                localUseCase.observeMonthlyExpense(currentYearMonth),
                localUseCase.observeMonthlyExpense(lastYearMonth),
                localUseCase.observeExpenseByCategory()
            ) { currentExpense, lastExpense, categories ->
                val diff = if (lastExpense > 0) {
                    ((lastExpense - currentExpense) / lastExpense * 100).toFloat()
                } else 0f

                AnalyticsUIState(
                    isLoading = false,
                    totalExpense = currentExpense,
                    lastMonthExpense = lastExpense,
                    categoryBreakdown = categories,
                    topCategory = categories.maxByOrNull { it.total },
                    savingPercent = kotlin.math.abs(diff),
                    isSavingMore = currentExpense < lastExpense
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
