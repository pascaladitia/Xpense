package com.pascal.xpense.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

expect fun showToast(msg: String)

expect fun actionShareUrl(url: String?)

fun addRandomParam(url: String?): String? {
    if (url == null) return null

    return when {
        url.contains("random=") -> "$url${(0..9999).random()}"
        url.contains("?random") -> "$url${(0..9999).random()}"
        else -> url
    }
}

fun String.toEnglishDate(): String {
    val parts = this.split("-")
    if (parts.size != 3) return this

    val year = parts[0]
    val month = parts[1].toIntOrNull() ?: return this
    val day = parts[2]

    val monthName = month.toEnglishMonthName()

    return "$day $monthName $year"
}

fun Int.toEnglishMonthName(): String {
    return when (this) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}

fun formatAmount(amount: Double): String {
    val absAmount = kotlin.math.abs(amount)
    val wholePart = absAmount.toLong()
    val fractional = ((absAmount - wholePart) * 100 + 0.5).toLong().coerceAtMost(99)
    val sign = if (amount < 0) "-" else ""
    return if (fractional == 0L) {
        "$sign$wholePart"
    } else {
        val fracStr = if (fractional < 10) "0$fractional" else fractional.toString()
        "$sign$wholePart.$fracStr"
    }
}

fun formatDateHeader(date: String): String {
    val parts = date.split("-")
    return if (parts.size == 3) {
        val month = parts[1].toIntOrNull()?.toEnglishMonthName() ?: parts[1]
        "${parts[2]} $month ${parts[0]}"
    } else date
}

fun getCategoryIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "food" -> Icons.Filled.Restaurant
        "transport" -> Icons.Filled.DirectionsCar
        "shopping" -> Icons.Filled.ShoppingCart
        "bills" -> Icons.Filled.Receipt
        "salary" -> Icons.Filled.AccountBalance
        "freelance" -> Icons.Filled.Computer
        "investment" -> Icons.Filled.TrendingUp
        "gift" -> Icons.Filled.CardGiftcard
        else -> Icons.Filled.Receipt
    }
}