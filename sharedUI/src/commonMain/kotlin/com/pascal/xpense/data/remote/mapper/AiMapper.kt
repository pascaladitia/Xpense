package com.pascal.xpense.data.remote.mapper

import com.pascal.xpense.data.remote.dtos.ChatMessageDto
import com.pascal.xpense.data.remote.dtos.ChatRequest
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.data.remote.dtos.TransactionAiDto
import com.pascal.xpense.utils.Constant
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
                    put("text", text.ifBlank { "Look at this image and describe it." })
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

object TransactionType {
    const val INCOME = "INCOME"
    const val EXPENSE = "EXPENSE"
}
