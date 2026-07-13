package com.pascal.xpense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.RoomDatabase
import com.pascal.xpense.data.local.database.AppDatabase
import com.pascal.xpense.ui.navigation.RouteScreen
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.ui.theme.LocalThemeIsDark
import com.russhwolf.settings.Settings

@Preview
@Composable
fun App(
    onThemeChanged: @Composable (isDark: Boolean) -> Unit = {}
) = AppTheme(onThemeChanged) {

    var isDark by LocalThemeIsDark.current

    LaunchedEffect(Unit) {
        isDark = true
    }

    RouteScreen()
}

expect fun createSettings(): Settings

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
