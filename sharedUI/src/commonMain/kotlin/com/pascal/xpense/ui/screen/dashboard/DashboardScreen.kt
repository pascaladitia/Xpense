package com.pascal.xpense.ui.screen.dashboard

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.ui.screen.dashboard.state.DashboardUIState
import com.pascal.xpense.ui.screen.dashboard.state.LocalDashboardEvent
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.ui.theme.CoralExpense

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    uiState: DashboardUIState = DashboardUIState()
) {
    val event = LocalDashboardEvent.current

    Box(modifier = modifier.fillMaxSize()) {
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
                    HeaderSection()
                }

                item {
                    ExpenseSection(totalExpense = uiState.totalExpense)
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

@Composable
private fun HeaderSection() {
    Text(
        text = "ExpenseTracker",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ExpenseSection(totalExpense: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Expenses",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${formatAmount(totalExpense)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = CoralExpense
            )
        }
    }
}

@Composable
fun TransactionItem(
    transaction: TransactionEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getCategoryIcon(transaction.category),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "$${formatAmount(transaction.amount)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = CoralExpense
            )
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No transactions yet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Start tracking your expenses to see them here",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun formatAmount(amount: Double): String {
    val wholePart = amount.toLong()
    val fractional = ((amount - wholePart) * 100).toLong()
    return if (fractional == 0L) {
        wholePart.toString()
    } else {
        val fracStr = if (fractional < 10) "0$fractional" else fractional.toString()
        "$wholePart.$fracStr"
    }
}

private fun formatDateHeader(date: String): String {
    val parts = date.split("-")
    return if (parts.size == 3) {
        val month = parts[1].toIntOrNull()?.toEnglishMonthName() ?: parts[1]
        "${parts[2]} $month ${parts[0]}"
    } else date
}

private fun Int.toEnglishMonthName(): String = when (this) {
    1 -> "January"; 2 -> "February"; 3 -> "March"; 4 -> "April"
    5 -> "May"; 6 -> "June"; 7 -> "July"; 8 -> "August"
    9 -> "September"; 10 -> "October"; 11 -> "November"; 12 -> "December"
    else -> ""
}

private fun getCategoryIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "shopping" -> Icons.Filled.ShoppingCart
        "food" -> Icons.Filled.Fastfood
        "travel" -> Icons.Filled.Flight
        else -> Icons.Filled.ShoppingCart
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    AppTheme { DashboardScreen() }
}
