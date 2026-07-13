package com.pascal.xpense.ui.screen.search

import androidx.lifecycle.ViewModel
import com.pascal.xpense.ui.screen.profile.state.ProfileUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    fun resetError() {
        _uiState.update { it.copy(error = false to "") }
    }
}

enum class ThemeMode { SYSTEM, LIGHT, DARK }