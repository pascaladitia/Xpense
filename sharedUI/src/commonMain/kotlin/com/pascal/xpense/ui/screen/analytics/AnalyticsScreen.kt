package com.pascal.xpense.ui.screen.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.xpense.domain.model.CategoryBreakdown
import com.pascal.xpense.ui.component.screenUtils.TopAppBarComponent
import com.pascal.xpense.ui.screen.analytics.state.AnalyticsUIState
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.ui.theme.DeepNavy
import com.pascal.xpense.ui.theme.GreenIncome
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.analytics
import xpense.sharedui.generated.resources.category_breakdown
import xpense.sharedui.generated.resources.loading
import xpense.sharedui.generated.resources.no_expense_data
import xpense.sharedui.generated.resources.saving_insight
import xpense.sharedui.generated.resources.spent_less
import xpense.sharedui.generated.resources.spent_more
import xpense.sharedui.generated.resources.spending_overview
import xpense.sharedui.generated.resources.top_spending
import xpense.sharedui.generated.resources.total_spent_this_month

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    uiState: AnalyticsUIState = AnalyticsUIState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.analytics),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(Res.string.loading), style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            SpendingOverview(totalExpense = uiState.totalExpense)

            Spacer(modifier = Modifier.height(16.dp))

            uiState.topCategory?.let { top ->
                TopSpendingCard(category = top)
                Spacer(modifier = Modifier.height(16.dp))
            }

            CategoryBreakdownSection(categories = uiState.categoryBreakdown)

            Spacer(modifier = Modifier.height(16.dp))

            SavingInsightCard(
                percent = uiState.savingPercent,
                isSavingMore = uiState.isSavingMore
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SpendingOverview(totalExpense: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.spending_overview),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$${formatAmount(totalExpense)}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.total_spent_this_month),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TopSpendingCard(category: CategoryBreakdown) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavy),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.top_spending),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.category,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${formatAmount(category.total)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun CategoryBreakdownSection(categories: List<CategoryBreakdown>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.category_breakdown),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            categories.forEach { category ->
                val barColor = when (category.category.lowercase()) {
                    "shopping" -> Color(0xFF8B5CF6)
                    "food" -> Color(0xFFF59E0B)
                    "travel" -> Color(0xFF3B82F6)
                    else -> Color(0xFF6B7280)
                }
                CategoryBar(
                    label = category.category,
                    amount = category.total,
                    percentage = category.percentage,
                    color = barColor
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (categories.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_expense_data),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CategoryBar(
    label: String,
    amount: Double,
    percentage: Float,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "$${formatAmount(amount)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(4.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage)
                    .height(8.dp)
                    .background(color, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
private fun SavingInsightCard(
    percent: Float,
    isSavingMore: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isSavingMore) Icons.Default.TrendingDown else Icons.Default.TrendingUp,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = if (isSavingMore) GreenIncome else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = stringResource(Res.string.saving_insight),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = if (isSavingMore) {
                        stringResource(Res.string.spent_less, percent.toInt())
                    } else {
                        stringResource(Res.string.spent_more, percent.toInt())
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatAmount(amount: Double): String {
    val absAmount = kotlin.math.abs(amount)
    val wholePart = absAmount.toLong()
    val fractional = ((absAmount - wholePart) * 100 + 0.5).toLong()
    val sign = if (amount < 0) "-" else ""
    return if (fractional == 0L) {
        "$sign$wholePart"
    } else {
        val fracStr = if (fractional < 10) "0$fractional" else fractional.toString()
        "$sign$wholePart.$fracStr"
    }
}

@Preview(showBackground = true)
@Composable
private fun AnalyticsPreview() {
    AppTheme { AnalyticsScreen() }
}
