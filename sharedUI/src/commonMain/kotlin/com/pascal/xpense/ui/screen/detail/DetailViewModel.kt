@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.pascal.xpense.ui.screen.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.lifecycle.ViewModel
import com.pascal.xpense.domain.usecase.local.LocalUseCase

class DetailViewModel(
    private val localUseCase: LocalUseCase
) : ViewModel() {
}