package com.pascal.xpense.data.preferences

import com.pascal.xpense.createSettings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object PrefsTheme {

    private const val THEME_MODE_KEY = "theme_mode"
    private const val DEFAULT_MODE = "SYSTEM"

    private val settings by lazy { createSettings() }

    private val _themeFlow = MutableStateFlow(getThemeMode())
    val themeFlow: Flow<String> = _themeFlow

    fun observeThemeMode(): Flow<String> = themeFlow

    fun saveThemeMode(mode: String) {
        settings[THEME_MODE_KEY] = mode
        _themeFlow.value = mode
    }

    fun getThemeMode(): String {
        return settings[THEME_MODE_KEY, DEFAULT_MODE]
    }

    fun clear() {
        settings.clear()
        _themeFlow.value = DEFAULT_MODE
    }
}
