@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.pascal.xpense.ui.screen.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.pascal.xpense.domain.model.AnimeItem
import com.pascal.xpense.domain.usecase.remote.RemoteUseCase
import com.pascal.xpense.ui.screen.home.state.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remoteUseCase: RemoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    private val _animeLiveResponse = MutableStateFlow(PagingData.empty<AnimeItem>())
    val animeLiveResponse: StateFlow<PagingData<AnimeItem>> = _animeLiveResponse

    fun setTransition(
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        _uiState.update {
            it.copy(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    }

    fun loadAnimeHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            remoteUseCase.getAnimeHome()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = true to e.message.toString()
                        )
                    }
                }
                .collect { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            anime = result
                        )
                    }
                }
        }
    }

    fun loadAnimeLive() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            remoteUseCase.getAnimeLive()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = true to e.message.toString()
                        )
                    }
                }
                .collect { result ->
                    _uiState.update { it.copy(isLoading = false) }
                    _animeLiveResponse.value = result
                }
        }
    }

    fun resetError() {
        _uiState.update { it.copy(error = false to "") }
    }
}