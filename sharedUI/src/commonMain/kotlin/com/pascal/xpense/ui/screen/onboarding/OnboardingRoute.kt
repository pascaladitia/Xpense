package com.pascal.xpense.ui.screen.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.pascal.xpense.data.preferences.PrefLogin
import com.pascal.xpense.ui.screen.onboarding.state.LocalOnboardingEvent

@Composable
fun OnboardingRoute(
    onNext: () -> Unit
) {
    val event = LocalOnboardingEvent.current

    CompositionLocalProvider(
        LocalOnboardingEvent provides event.copy(
            onNext = {
                PrefLogin.setIsOnboarding(true)
                onNext()
            }
        )
    ) {
        OnboardingScreen()
    }
}