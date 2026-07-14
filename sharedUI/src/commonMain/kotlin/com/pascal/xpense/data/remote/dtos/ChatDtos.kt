package com.pascal.xpense.data.remote.dtos

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessageDto>
)

@Serializable
data class ChatMessageDto(
    val role: String,
    val content: JsonElement
)

@Serializable
data class ChatResponse(
    val choices: List<ChatChoice> = emptyList(),
    val error: ApiError? = null
)

@Serializable
data class ChatChoice(
    val message: ApiMessage = ApiMessage(),
    val finish_reason: String? = null
)

@Serializable
data class ApiMessage(
    val role: String = "",
    val content: String = ""
)

@Serializable
data class ApiError(
    val message: String? = null
)

@Serializable
data class TransactionAiDto(
    val type: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val category: String? = null,
    val date: String? = null
)

data class ChatTurn(
    val role: String,
    val text: String,
    val imageDataUrl: String? = null
)
