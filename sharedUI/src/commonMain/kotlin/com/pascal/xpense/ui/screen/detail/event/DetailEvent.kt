package com.pascal.xpense.ui.screen.detail.event

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalDetailEvent = compositionLocalOf { DetailEvent() }

@Stable
data class DetailEvent(
    val onNavBack: () -> Unit = {},
    val onAudio: (String?) -> Unit = {},
    val onShare: (String?) -> Unit = {}
)