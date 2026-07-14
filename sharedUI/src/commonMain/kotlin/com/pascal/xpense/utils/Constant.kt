package com.pascal.xpense.utils

object Constant {
    const val TIMEOUT = 300000L
    const val ERROR_MESSAGE = "Something went wrong, check your connection!"
    const val FORMAT_DATE = "yyyy-MM-dd"

    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/"
    const val YOUTUBE_TN_URL  = "https://img.youtube.com/vi/"
    const val YOUTUBE_TRAILERS_URL = "https://www.youtube.com/watch?v="
    const val W185 = "w185"
    const val W500 = "w500"

    // Dahl AI Chat
    const val AI_BASE_URL = "https://inference.dahl.global/v1/chat/completions"
    const val AI_MODEL = "MiniMaxAI/MiniMax-M2.7"

    val EXPENSE_CATEGORIES = listOf("Food", "Transport", "Shopping", "Other")
    val INCOME_CATEGORIES = listOf("Salary", "Freelance", "Investment", "Other")
}