package com.pascal.xpense.ui.screen.dashboard.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.theme.CoralExpense
import com.pascal.xpense.ui.theme.GreenIncome
import com.pascal.xpense.utils.formatAmount
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.expense
import xpense.sharedui.generated.resources.income

@Composable
fun IncomeExpenseRow(
    modifier: Modifier = Modifier,
    income: Double,
    expense: Double
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        IncomeExpenseCard(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.income),
            value = income,
            isIncome = true
        )

        Spacer(Modifier.width(16.dp))

        IncomeExpenseCard(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.expense),
            value = expense,
            isIncome = false
        )
    }
}

@Composable
private fun IncomeExpenseCard(
    modifier: Modifier = Modifier,
    label: String,
    value: Double,
    isIncome: Boolean
) {
    val type = if (isIncome) "+" else "-"
    val color = if (isIncome) GreenIncome else CoralExpense
    val icon = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(Modifier.width(8.dp))

                Text(
                    text = label.uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$type$${formatAmount(value)}",
                style = MaterialTheme.typography.headlineSmall,
                color = color
            )
        }
    }
}