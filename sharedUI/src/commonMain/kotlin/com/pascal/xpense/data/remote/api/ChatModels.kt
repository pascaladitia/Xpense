package com.pascal.xpense.data.remote.api

import com.pascal.xpense.utils.Constant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * System prompt and static text used when talking to the AI.
 * Kept in the model layer so the ViewModel stays clean.
 */
object ChatPrompt {
    val system: String = """
You are a personal finance assistant for an expense tracker app.
Help the user manage their income and expenses through conversation.
When the user wants to record a transaction, respond with ONLY a JSON object (no markdown, no extra text) in this exact format:
{"type":"INCOME" or "EXPENSE","title":"short description","amount":<number>,"category":"<allowed category>","date":"YYYY-MM-DD"}
Allowed expense categories: Food, Transport, Shopping, Other.
Allowed income categories: Salary, Freelance, Investment, Other.
If the category is not in the allowed list or is missing, use "Other".
If the date is not given, use today's date.
For anything else, reply with a short, friendly, helpful message in the user's language.
""".trimIndent()
}

/**
 * A single turn in the conversation sent to the AI.
 * [imageDataUrl] is an optional base64 data URL used for image input.
 */
data class ChatTurn(
    val role: String,
    val text: String,
    val imageDataUrl: String? = null
)

/**
 * Transaction structure the AI is expected to return as a JSON object.
 */
@Serializable
data class AITransaction(
    val type: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val category: String? = null,
    val date: String? = null
)

/**
 * Extracts a [AITransaction] from an AI reply. The model may wrap the JSON in
 * code fences or add explanatory text, so we grab the first balanced `{ ... }`.
 * Returns null when no valid transaction object is found.
 */
fun parseAITransaction(content: String): AITransaction? {
    val start = content.indexOf('{')
    val end = content.lastIndexOf('}')
    if (start < 0 || end <= start) return null

    val json = content.substring(start, end + 1)
    return try {
        Json { ignoreUnknownKeys = true }.decodeFromString<AITransaction>(json)
    } catch (_: Exception) {
        null
    }
}

/** Normalizes user/AI type strings into the stored representation. */
fun normalizeTransactionType(raw: String): String {
    return when (raw.lowercase().trim()) {
        "income", "in", "pemasukan", "debit" -> TransactionType.INCOME
        "expense", "ex", "pengeluaran", "credit" -> TransactionType.EXPENSE
        else -> raw.uppercase().trim()
    }
}

/** Maps a category to an allowed value, falling back to "Other". */
fun normalizeCategory(type: String, raw: String?): String {
    val allowed = if (type == TransactionType.INCOME) Constant.INCOME_CATEGORIES else Constant.EXPENSE_CATEGORIES
    val cleaned = raw?.trim().orEmpty()
    return if (cleaned.isNotEmpty() && cleaned in allowed) cleaned else "Other"
}

object TransactionType {
    const val INCOME = "INCOME"
    const val EXPENSE = "EXPENSE"
}
