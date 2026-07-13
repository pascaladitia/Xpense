package com.pascal.xpense.ui.screen.favorite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pascal.xpense.ui.theme.AppTheme
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun FavoriteRoute(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: FavoriteViewModel = koinInject<FavoriteViewModel>(),
    onDetail: () -> Unit
) {

}


@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    AppTheme {  }
}