@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.pascal.xpense.ui.screen.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.label_completed
import xpense.sharedui.generated.resources.label_ongoing
import app.cash.paging.compose.LazyPagingItems
import com.pascal.xpense.domain.model.AnimeItem
import com.pascal.xpense.ui.component.screenUtils.shimmer
import com.pascal.xpense.ui.screen.home.component.HomeLiveItem
import com.pascal.xpense.ui.screen.home.component.HomeOngoingItem
import com.pascal.xpense.ui.screen.home.component.LazyRowCarousel
import com.pascal.xpense.ui.screen.home.state.HomeUIState
import com.pascal.xpense.ui.screen.onboarding.state.LocalOnboardingEvent
import com.pascal.xpense.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUIState = HomeUIState(),
    animeLiveResponse: LazyPagingItems<AnimeItem>? = null
) {
    val event = LocalOnboardingEvent.current

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            HomeLiveItem(
                animeLiveResponse = animeLiveResponse
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.label_ongoing),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                uiState.sharedTransitionScope?.let {
                    with(it) {
                        LazyRowCarousel(
                            isLoading = uiState.isLoading,
                            items = uiState.anime?.ongoing,
                            animatedVisibilityScope = uiState.animatedVisibilityScope!!
                        ) {
                            event.onNext()
                        }
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.label_completed),
                    style = MaterialTheme.typography.titleLarge
                )

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (uiState.isLoading) {
            items(2) { index ->
                Box(
                    modifier = Modifier
                        .padding(
                            start = if (index % 2 == 0) 16.dp else 8.dp,
                            end = if (index % 2 == 0) 8.dp else 16.dp
                        )
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmer()
                )
            }
        } else {
            itemsIndexed(uiState.anime?.completed?.animeList.orEmpty()) { index, items ->
                HomeOngoingItem(
                    modifier = Modifier.padding(
                        start = if (index % 2 == 0) 16.dp else 8.dp,
                        end = if (index % 2 == 0) 8.dp else 16.dp
                    ),
                    items = items
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    AppTheme {
        HomeScreen()
    }
}