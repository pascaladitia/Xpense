package com.pascal.xpense.ui.screen.budget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.component.screenUtils.TopAppBarComponent
import com.pascal.xpense.ui.screen.budget.component.BudgetOverviewCard
import com.pascal.xpense.ui.screen.budget.component.CategoryBudgetCard
import com.pascal.xpense.ui.screen.budget.state.BudgetUIState
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.ui.theme.DeepNavy

@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    uiState: BudgetUIState = BudgetUIState()
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBarComponent(
            title = "Budget",
            color = DeepNavy
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

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
}

@Preview(showBackground = true)
@Composable
private fun BudgetPreview() {
    AppTheme { BudgetScreen() }
}
