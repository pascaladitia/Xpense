package com.pascal.xpense.domain.model

data class StockRecommendation(
    val code: String,
    val priceNow: String,
    val change: String,
    val recommendation: String,
    val buyRange: String?,
    val targetPrice: String?,
    val targetGain: String?,
    val stopLoss: String?,
    val stopLossPercent: String?,
    val sellPrice: String?,
    val note: String?,
    val returnValue: String?,
    val status: String?
)
