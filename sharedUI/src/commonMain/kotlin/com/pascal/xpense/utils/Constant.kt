package com.pascal.xpense.utils

object Constant {
    const val TIMEOUT = 300000L

    const val AI_MODEL = "llama-3.3-70b-versatile"

    val EXPENSE_CATEGORIES = listOf("Food", "Transport", "Shopping", "Other")
    val INCOME_CATEGORIES = listOf("Salary", "Freelance", "Investment", "Other")

    const val SYSTEM_PROMPT = """You are a personal finance assistant for an expense tracker app.
Help the user manage their income and expenses through conversation.
When the user wants to record a transaction, respond with ONLY a JSON object (no markdown, no extra text) in this exact format:

{"type":"INCOME" or "EXPENSE","title":"short description","amount":<number>,"category":"<allowed category>","date":"YYYY-MM-DD"}

Allowed expense categories: Food, Transport, Shopping, Other.
Allowed income categories: Salary, Freelance, Investment, Other.
If the category is not in the allowed list or is missing, use "Other".
If the date is not given, use today's date.
For anything else, reply with a short, friendly, helpful message in the user's language."""

    const val AI_ROLE_SYSTEM = "system"
    const val AI_ROLE_USER = "user"
    const val AI_ROLE_ASSISTANT = "assistant"

    const val AI_WELCOME_MESSAGE = "Hello! I'm your finance assistant. Tell me about your income or expenses, or send a receipt photo, and I'll record it to the database."
    const val AI_IMAGE_FALLBACK_TEXT = "Look at this image and record the transaction if any."
    const val DATA_IMAGE_JPEG_PREFIX = "data:image/jpeg;base64,"
    const val UNKNOWN_ERROR = "Something went wrong"
    const val DEFAULT_TITLE = "Transaction"
    const val INCOME_LABEL = "Income"
    const val EXPENSE_LABEL = "Expense"
}