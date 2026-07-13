package com.pascal.xpense.ui.screen.onboarding.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

val LocalOnboardingEvent = compositionLocalOf { OnboardingEvent() }

@Stable
data class OnboardingEvent(
    val onNext: () -> Unit = {}
)