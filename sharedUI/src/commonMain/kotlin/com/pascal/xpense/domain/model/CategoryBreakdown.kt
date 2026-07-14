package com.pascal.xpense.domain.model

data class CategoryBreakdown(
    val category: String,
    val total: Double,
    val percentage: Float = 0f
)
