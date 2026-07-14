package com.pascal.xpense.ui.screen.chat.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalChatEvent = compositionLocalOf { ChatEvent() }

@Stable
data class ChatEvent(
    val onInputChange: (String) -> Unit = {},
    val onSend: () -> Unit = {},
    val onAttachClick: () -> Unit = {},
    val onImagePicked: (ByteArray?, String) -> Unit = { _, _ -> },
    val onClearImage: () -> Unit = {},
    val onDismissSheet: () -> Unit = {}
)
