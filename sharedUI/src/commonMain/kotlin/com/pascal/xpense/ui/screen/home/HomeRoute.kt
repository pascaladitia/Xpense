@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.pascal.xpense.ui.screen.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.close
import app.cash.paging.compose.collectAsLazyPagingItems
import com.pascal.xpense.data.local.entity.FavoritesEntity
import com.pascal.xpense.ui.component.dialog.ShowDialog
import com.pascal.xpense.ui.component.screenUtils.PullRefreshComponent
import com.pascal.xpense.ui.screen.home.state.LocalHomeEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun HomeRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = koinInject<HomeViewModel>(),
    onDetail: (FavoritesEntity?) -> Unit
) {
    val event = LocalHomeEvent.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val animeLiveResponse = viewModel.animeLiveResponse.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.setTransition(sharedTransitionScope, animatedVisibilityScope)
        viewModel.loadAnimeHome()
        viewModel.loadAnimeLive()
    }

    if (uiState.error.first) {
        ShowDialog(
            message = uiState.error.second,
            textButton = stringResource(Res.string.close)
        ) {
            viewModel.resetError()
        }
    }

    CompositionLocalProvider(
        LocalHomeEvent provides event.copy(
            onDetail = onDetail
        )
    ) {
        PullRefreshComponent(
            onRefresh = {
                viewModel.loadAnimeHome()
                viewModel.loadAnimeLive()
            }
        ) {
            HomeScreen(
                uiState = uiState,
                animeLiveResponse = animeLiveResponse
            )
        }
    }
}