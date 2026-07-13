package com.pascal.xpense.ui.screen.manga.state

import com.pascal.xpense.domain.model.StockRecommendation

data class MangaUIState(
    val isLoading: Boolean = false,
    val error: Pair<Boolean, String> = false to "",
    val stockRecommendation: List<StockRecommendation>? = null
)