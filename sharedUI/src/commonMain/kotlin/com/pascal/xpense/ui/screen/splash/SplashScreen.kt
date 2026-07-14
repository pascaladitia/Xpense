package com.pascal.xpense.ui.screen.splash

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.app_name
import xpense.sharedui.generated.resources.logo

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        with(sharedTransitionScope) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(160.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(Res.string.app_name),
                        animatedVisibilityScope = animatedVisibilityScope,
                        renderInOverlayDuringTransition = true
                    )
            )
        }
    }
}
