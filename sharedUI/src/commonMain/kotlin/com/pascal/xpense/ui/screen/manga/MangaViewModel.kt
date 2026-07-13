package com.pascal.xpense.ui.screen.manga

import androidx.lifecycle.ViewModel
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.domain.usecase.remote.RemoteUseCase
import com.pascal.xpense.ui.screen.manga.state.MangaUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MangaViewModel(
    private val remoteUseCase: RemoteUseCase,
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MangaUIState())
    val uiState: StateFlow<MangaUIState> = _uiState.asStateFlow()

    fun loadInit() {

    }

    fun resetError() {
        _uiState.update { it.copy(error = false to "") }
    }
}