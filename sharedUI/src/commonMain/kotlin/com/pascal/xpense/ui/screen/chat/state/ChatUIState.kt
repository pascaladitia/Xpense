package com.pascal.xpense.ui.screen.chat.state

data class ChatUIState(
    val messages: List<ChatMessage> = emptyList(),
    val input: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val pendingImage: PendingImage? = null
)

data class ChatMessage(
    val id: String,
    val role: String, // "user" | "assistant"
    val text: String,
    val raw: String? = null, // raw AI content kept for conversation history
    val imageBytes: ByteArray? = null // user image (for preview only)
)

data class PendingImage(
    val bytes: ByteArray,
    val name: String
)
