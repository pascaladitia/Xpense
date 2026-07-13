@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.pascal.xpense.ui.screen.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import com.pascal.xpense.data.local.entity.FavoritesEntity
import org.koin.compose.koinInject

@Composable
fun DetailRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: DetailViewModel = koinInject<DetailViewModel>(),
    item: FavoritesEntity? = null,
    onNavBack: () -> Unit
) {

}