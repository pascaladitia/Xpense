package com.pascal.xpense.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.pascal.xpense.ui.screen.profile.state.ProfileUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()
}

enum class ThemeMode { SYSTEM, LIGHT, DARK }
