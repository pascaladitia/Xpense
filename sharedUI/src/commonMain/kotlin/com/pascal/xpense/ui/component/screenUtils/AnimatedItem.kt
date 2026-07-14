package com.pascal.xpense.ui.component.screenUtils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

enum class SlideDirection { NONE, LEFT, RIGHT, UP, DOWN }

private val LocalStaggeredVisible = staticCompositionLocalOf { true }
private val LocalStaggeredTotalItems = staticCompositionLocalOf { 1 }

@Composable
fun StaggeredScope(
    visible: Boolean,
    totalItems: Int,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalStaggeredVisible provides visible,
        LocalStaggeredTotalItems provides totalItems,
        content = content,
    )
}

@Composable
fun StaggeredAnimatedItem(
    modifier: Modifier = Modifier,
    index: Int,
    visible: Boolean = LocalStaggeredVisible.current,
    totalItems: Int = LocalStaggeredTotalItems.current,
    slideDirection: SlideDirection = SlideDirection.LEFT,
    staggerMillis: Int = 60,
    enterDurationMillis: Int = 350,
    exitDurationMillis: Int = 250,
    hiddenOffsetPx: Float = 72f,
    content: @Composable () -> Unit,
) {
    val delayMillis = if (visible) {
        index * staggerMillis
    } else {
        (totalItems - 1 - index) * staggerMillis
    }
    val durationMillis = if (visible) enterDurationMillis else exitDurationMillis

    val targetAlpha = if (visible) 1f else 0f
    val targetTranslationX = when (slideDirection) {
        SlideDirection.LEFT -> if (visible) 0f else -hiddenOffsetPx
        SlideDirection.RIGHT -> if (visible) 0f else hiddenOffsetPx
        else -> 0f
    }
    val targetTranslationY = when (slideDirection) {
        SlideDirection.UP -> if (visible) 0f else -hiddenOffsetPx
        SlideDirection.DOWN -> if (visible) 0f else hiddenOffsetPx
        else -> 0f
    }

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "staggeredAlpha",
    )
    val translationX by animateFloatAsState(
        targetValue = targetTranslationX,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "staggeredTranslationX",
    )
    val translationY by animateFloatAsState(
        targetValue = targetTranslationY,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "staggeredTranslationY",
    )

    Box(
        modifier = modifier.graphicsLayer {
                this.alpha = alpha
                this.translationX = translationX
                this.translationY = translationY
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        content()
    }
}
