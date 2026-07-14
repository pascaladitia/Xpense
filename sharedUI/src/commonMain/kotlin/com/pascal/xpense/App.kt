package com.pascal.xpense

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.RoomDatabase
import com.pascal.xpense.data.local.database.AppDatabase
import com.pascal.xpense.ui.navigation.RouteScreen
import com.pascal.xpense.ui.theme.AppTheme

@Preview
@Composable
fun App(
    onThemeChanged: @Composable (isDark: Boolean) -> Unit = {}
) = AppTheme(onThemeChanged) {

    RouteScreen()
}

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
