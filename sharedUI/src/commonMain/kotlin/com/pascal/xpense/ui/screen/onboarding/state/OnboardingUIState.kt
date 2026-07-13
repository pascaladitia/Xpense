package com.pascal.xpense.ui.screen.onboarding.state

data class OnboardingUIState(
    val isLoading: Boolean = false,
    val error: Pair<Boolean, String> = false to "",
)