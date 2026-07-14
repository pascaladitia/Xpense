package com.pascal.xpense.utils

object Constant {
    const val TIMEOUT = 300000L

    // Groq AI Chat (OpenAI-compatible). Free high-limit model.
    const val AI_BASE_URL = "https://api.groq.com/openai/v1/chat/completions"
    const val AI_MODEL = "llama-3.3-70b-versatile"

    val EXPENSE_CATEGORIES = listOf("Food", "Transport", "Shopping", "Other")
    val INCOME_CATEGORIES = listOf("Salary", "Freelance", "Investment", "Other")
}