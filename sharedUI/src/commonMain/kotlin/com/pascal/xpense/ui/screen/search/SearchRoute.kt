package com.pascal.xpense.ui.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pascal.xpense.ui.theme.AppTheme
import org.koin.compose.koinInject

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: SearchViewModel = koinInject<SearchViewModel>(),
    onDetail: () -> Unit
) {

}


@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    AppTheme {  }
}