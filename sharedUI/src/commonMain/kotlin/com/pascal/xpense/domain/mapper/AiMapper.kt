package com.pascal.xpense.domain.mapper

import com.pascal.xpense.data.remote.dtos.ChatMessageDto
import com.pascal.xpense.data.remote.dtos.ChatRequest
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.data.remote.dtos.TransactionAiDto
import com.pascal.xpense.utils.Constant
import com.pascal.xpense.utils.currentTimeMillis
import com.pascal.xpense.utils.formatAmount
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

private val parser = Json { ignoreUnknownKeys = true }

fun List<ChatTurn>.toChatRequest(model: String): ChatRequest =
    ChatRequest(
        model = model,
        messages = map { it.toMessageDto() }
    )

private fun ChatTurn.toMessageDto(): ChatMessageDto {
    val content: JsonElement = if (imageDataUrl != null) {
        buildJsonArray {
            add(
                buildJsonObject {
                    put("type", "text")
                    put("text", text)
                }
            )
            add(
                buildJsonObject {
                    put("type", "image_url")
                    putJsonObject("image_url") {
                        put("url", imageDataUrl)
                    }
                }
            )
        }
    } else {
        JsonPrimitive(text)
    }
    return ChatMessageDto(role = role, content = content)
}

fun parseAITransaction(content: String): TransactionAiDto? {
    val start = content.indexOf('{')
    val end = content.lastIndexOf('}')
    if (start !in 0..<end) return null

    val json = content.substring(start, end + 1)
    return try {
        parser.decodeFromString<TransactionAiDto>(json)
    } catch (_: Exception) {
        null
    }
}

fun normalizeTransactionType(raw: String): String {
    return when (raw.lowercase().trim()) {
        "income", "in", "pemasukan", "debit" -> TransactionType.INCOME
        "expense", "ex", "pengeluaran", "credit" -> TransactionType.EXPENSE
        else -> raw.uppercase().trim()
    }
}

fun normalizeCategory(type: String, raw: String?): String {
    val allowed = if (type == TransactionType.INCOME) Constant.INCOME_CATEGORIES else Constant.EXPENSE_CATEGORIES
    val cleaned = raw?.trim().orEmpty()
    return if (cleaned.isNotEmpty() && cleaned in allowed) cleaned else "Other"
}

fun isKnownTransactionType(raw: String): Boolean {
    val t = normalizeTransactionType(raw)
    return t == TransactionType.INCOME || t == TransactionType.EXPENSE
}

fun resolveDate(raw: String?): String {
    return if (!raw.isNullOrBlank() && raw.matches(Regex("^\\d{4}-\\d{2}-\\d{2}\$"))) raw else today()
}

private fun today(): String {
    val now = Instant
        .fromEpochMilliseconds(currentTimeMillis())
        .toLocalDateTime(TimeZone.currentSystemDefault())
    return "${now.year}-${now.month.number.toString().padStart(2, '0')}-${now.day.toString().padStart(2, '0')}"
}

fun formatConfirmation(type: String, title: String, amount: Double, category: String, date: String): String {
    val kind = if (type == TransactionType.INCOME) Constant.INCOME_LABEL else Constant.EXPENSE_LABEL
    val displayTitle = title.ifBlank { Constant.DEFAULT_TITLE }
    val formatted = formatAmount(amount)
    return "✅ ${kind} recorded:\n${displayTitle} • ${'$'}${formatted} • ${category} • ${date}"
}

object TransactionType {
    const val INCOME = "INCOME"
    const val EXPENSE = "EXPENSE"
}
