package com.pascal.xpense.ui.screen.manga.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalMangaEvent = compositionLocalOf { MangaEvent() }

@Stable
data class MangaEvent(
    val onDetail: () -> Unit = {}
)