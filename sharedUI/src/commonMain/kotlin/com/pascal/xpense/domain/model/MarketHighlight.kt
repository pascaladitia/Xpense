package com.pascal.xpense.domain.model

data class MarketHighlight(
    val section: String,
    val items: List<MarketHighlightData>
)

data class MarketHighlightData(
    val label: String,
    val title: String,
    val priceInfo: String?,
    val companyName: String?,
    val timeAgo: String?,
    val action: String?
)
