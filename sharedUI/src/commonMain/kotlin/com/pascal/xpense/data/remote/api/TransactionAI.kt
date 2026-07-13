package com.pascal.xpense.data.remote.api

import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
data class AIAnalysisResult(
    val totalSpending: Double,
    val topCategory: String,
    val categoryBreakdown: List<AICategoryBreakdown>,
    val insight: String,
    val savingTips: List<String>
)

@Serializable
data class AICategoryBreakdown(
    val category: String,
    val amount: Double,
    val percentage: Float
)

class TransactionAI {

    suspend fun analyzeTransactions(): AIAnalysisResult {
        delay(500)
        return AIAnalysisResult(
            totalSpending = 1250.0,
            topCategory = "Food",
            categoryBreakdown = listOf(
                AICategoryBreakdown("Food", 450.0, 0.36f),
                AICategoryBreakdown("Shopping", 350.0, 0.28f),
                AICategoryBreakdown("Travel", 250.0, 0.20f),
                AICategoryBreakdown("Other", 200.0, 0.16f)
            ),
            insight = "Your biggest expense this month is Food at 36% of total spending.",
            savingTips = listOf(
                "Try cooking at home more often to reduce food delivery expenses",
                "Set a weekly shopping budget and stick to it",
                "Consider using public transport to save on travel costs"
            )
        )
    }

    suspend fun getSavingSuggestion(amount: Double): String {
        delay(300)
        return when {
            amount > 1000 -> "Consider reviewing your large expenses — small cuts add up!"
            amount > 500 -> "You're doing okay! Try tracking daily expenses to find saving opportunities."
            else -> "Great job keeping expenses low! Keep up the good habits."
        }
    }
}
