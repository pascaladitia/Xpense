package com.pascal.xpense.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.component.screenUtils.TopAppBarComponent
import com.pascal.xpense.ui.screen.dashboard.component.BalanceSection
import com.pascal.xpense.ui.screen.dashboard.component.EmptyState
import com.pascal.xpense.ui.screen.dashboard.component.IncomeExpenseRow
import com.pascal.xpense.ui.screen.dashboard.component.TransactionItem
import com.pascal.xpense.ui.screen.dashboard.state.DashboardUIState
import com.pascal.xpense.ui.screen.dashboard.state.LocalDashboardEvent
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.ui.theme.DeepNavy
import com.pascal.xpense.utils.formatDateHeader

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    uiState: DashboardUIState = DashboardUIState()
) {
    val event = LocalDashboardEvent.current

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBarComponent(
            title = "ExpenseTracker",
            color = DeepNavy
        )

        Box(modifier = Modifier.weight(1f)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        BalanceSection(totalBalance = uiState.totalBalance)
                    }

                    item {
                        IncomeExpenseRow(
                            income = uiState.totalIncome,
                            expense = uiState.totalExpense
                        )
                    }

                    item {
                        Text(
                            text = "Transactions",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    if (uiState.transactions.isEmpty()) {
                        item { EmptyState() }
                    } else {
                        uiState.groupedTransactions.forEach { (date, transactions) ->
                            item {
                                Text(
                                    text = formatDateHeader(date),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                            items(transactions, key = { it.id }) { transaction ->
                                TransactionItem(
                                    transaction = transaction,
                                    onDelete = { event.onDeleteTransaction(transaction.id) }
                                )
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }

            FloatingActionButton(
                onClick = { event.onAddTransaction() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color(0xFF0F172A),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    AppTheme { DashboardScreen() }
}
