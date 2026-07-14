package com.pascal.xpense.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.component.screenUtils.StaggeredAnimatedItem
import com.pascal.xpense.ui.component.screenUtils.StaggeredScope
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
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    uiState: DashboardUIState = DashboardUIState()
) {
    val event = LocalDashboardEvent.current
    var isContentVisible by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }

    val transactionCount = if (uiState.transactions.isEmpty()) {
        1
    } else {
        var count = 0
        for (entry in uiState.groupedTransactions) {
            count += 1 + entry.value.size
        }
        count
    }
    val totalItems = 7 + transactionCount

    LaunchedEffect(Unit) {
        isContentVisible = true
    }

    LaunchedEffect(isContentVisible) {
        if (!isContentVisible && !isNavigating) {
            isNavigating = true
            delay((totalItems * 60L) + 350L)
            event.onAddTransaction()
        }
    }

    StaggeredScope(
        visible = isContentVisible,
        totalItems = totalItems,
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(key = "topbar") {
                    StaggeredAnimatedItem(index = 0) {
                        TopAppBarComponent(
                            title = "ExpenseTracker",
                            color = DeepNavy
                        )
                    }
                }

                item(key = "balance") {
                    StaggeredAnimatedItem(index = 1) {
                        BalanceSection(totalBalance = uiState.totalBalance)
                    }
                }

                item(key = "incomeExpense") {
                    StaggeredAnimatedItem(index = 2) {
                        IncomeExpenseRow(
                            income = uiState.totalIncome,
                            expense = uiState.totalExpense
                        )
                    }
                }

                item(key = "transactionsTitle") {
                    StaggeredAnimatedItem(index = 3) {
                        Text(
                            text = "Transactions",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                if (uiState.transactions.isEmpty()) {
                    item(key = "emptyState") {
                        StaggeredAnimatedItem(index = 4) {
                            EmptyState()
                        }
                    }
                } else {
                    uiState.groupedTransactions.forEach { (date, transactions) ->
                        item(key = "date_$date") {
                            StaggeredAnimatedItem(index = 5) {
                                Text(
                                    text = formatDateHeader(date),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                        items(transactions, key = { it.id }) { transaction ->
                            StaggeredAnimatedItem(index = 6) {
                                TransactionItem(
                                    transaction = transaction,
                                    onDelete = { event.onDeleteTransaction(transaction.id) }
                                )
                            }
                        }
                    }
                }

                item(key = "bottomSpacer") {
                    StaggeredAnimatedItem(
                        index = totalItems - 1,
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }

            StaggeredAnimatedItem(
                index = totalItems - 1,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(
                    onClick = {
                        isContentVisible = false
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color(0xFF0F172A),
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    AppTheme { DashboardScreen() }
}
