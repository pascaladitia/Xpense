package com.pascal.xpense.ui.component.screenUtils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.verticalFadeBackground(
    color: Color = Color.Black,
    isTop: Boolean
): Modifier = this.background(
    brush = Brush.verticalGradient(
        colors = if (isTop) {
            listOf(
                color,
                color.copy(alpha = 0.9f),
                color.copy(alpha = 0.8f),
                Color.Transparent
            )
        } else {
            listOf(
                Color.Transparent,
                color.copy(alpha = 0.8f),
                color.copy(alpha = 0.9f),
                color
            )
        },
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )
)

