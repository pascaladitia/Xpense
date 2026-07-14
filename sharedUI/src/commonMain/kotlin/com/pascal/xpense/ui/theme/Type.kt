package com.pascal.xpense.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.inter
import xpense.sharedui.generated.resources.inter_bold

@Composable
fun getTypography(): Typography {
    return Typography(
        headlineLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter_bold)),
            fontSize = 38.sp,
            lineHeight = 38.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter_bold)),
            fontSize = 28.sp,
            lineHeight = 32.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        titleSmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 26.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.inter)),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
    )
}
