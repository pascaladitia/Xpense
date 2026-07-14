package com.pascal.xpense.ui.screen.profile.state

import com.pascal.xpense.ui.screen.profile.ThemeMode

data class ProfileUIState(
    val isLoading: Boolean = false,
    val error: Pair<Boolean, String> = false to ""
)
