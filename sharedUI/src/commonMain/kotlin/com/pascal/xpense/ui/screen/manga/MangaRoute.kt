package com.pascal.xpense.ui.screen.manga

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.close
import com.pascal.xpense.ui.component.dialog.ShowDialog
import com.pascal.xpense.ui.component.screenUtils.LoadingScreen
import com.pascal.xpense.ui.screen.manga.state.LocalMangaEvent
import com.pascal.xpense.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun MangaRoute(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: MangaViewModel = koinInject<MangaViewModel>(),
    onDetail: () -> Unit
) {
    val event = LocalMangaEvent.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadInit()
    }

    if (uiState.isLoading) LoadingScreen()

    if (uiState.error.first) {
        ShowDialog(
            message = uiState.error.second,
            textButton = stringResource(Res.string.close)
        ) {
            viewModel.resetError()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchListPreview() {
    AppTheme {
    }
}