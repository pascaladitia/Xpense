package com.pascal.xpense.ui.screen.budget

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.screen.budget.state.BudgetUIState
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.ui.theme.CoralExpense
import com.pascal.xpense.ui.theme.GreenIncome

@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    uiState: BudgetUIState = BudgetUIState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Budget",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        BudgetOverviewCard(
            totalBudget = uiState.totalBudget,
            totalSpent = uiState.totalSpent
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Category Budgets",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.budgets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No budget data yet. Start adding expenses!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            uiState.budgets.forEach { (category, spent) ->
                CategoryBudgetCard(
                    category = category,
                    spent = spent,
                    limit = uiState.totalBudget / maxOf(uiState.budgets.size, 1)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun BudgetOverviewCard(totalBudget: Double, totalSpent: Double) {
    val percentage = if (totalBudget > 0) (totalSpent / totalBudget).toFloat() else 0f
    val remaining = totalBudget - totalSpent
    val isOverBudget = remaining < 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Monthly Budget",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "$${formatAmount(totalBudget)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(6.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage.coerceIn(0f, 1f))
                        .height(12.dp)
                        .background(
                            if (isOverBudget) CoralExpense else GreenIncome,
                            RoundedCornerShape(6.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Spent: $${formatAmount(totalSpent)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Remaining: $${formatAmount(maxOf(remaining, 0.0))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isOverBudget) CoralExpense else GreenIncome
                )
            }
        }
    }
}

@Composable
private fun CategoryBudgetCard(category: String, spent: Double, limit: Double) {
    val percentage = if (limit > 0) (spent / limit).toFloat() else 0f
    val isOver = spent > limit

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "$${formatAmount(spent)} / $${formatAmount(limit)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(3.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage.coerceIn(0f, 1f))
                        .height(6.dp)
                        .background(
                            if (isOver) CoralExpense else GreenIncome,
                            RoundedCornerShape(3.dp)
                        )
                )
            }
        }
    }
}

private fun formatAmount(amount: Double): String {
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

@Preview(showBackground = true)
@Composable
private fun BudgetPreview() {
    AppTheme { BudgetScreen() }
}
