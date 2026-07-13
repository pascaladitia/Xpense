package com.pascal.xpense.utils

import androidx.compose.runtime.Composable

@Composable
expect fun AppBackHandler(
    enabled: Boolean,
    onBack: () -> Unit
)