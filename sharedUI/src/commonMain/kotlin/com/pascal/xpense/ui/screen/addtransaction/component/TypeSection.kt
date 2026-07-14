package com.pascal.xpense.ui.screen.addtransaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.theme.CoralExpense
import com.pascal.xpense.ui.theme.GreenIncome
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.expense
import xpense.sharedui.generated.resources.income
import xpense.sharedui.generated.resources.type

@Composable
fun TypeSection(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(Res.string.type),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TypeOption(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.expense),
                isSelected = selectedType == "EXPENSE",
                color = CoralExpense,
                onClick = { onTypeSelected("EXPENSE") }
            )
            TypeOption(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.income),
                isSelected = selectedType == "INCOME",
                color = GreenIncome,
                onClick = { onTypeSelected("INCOME") }
            )
        }
    }
}

@Composable
private fun TypeOption(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(46.dp)
            .background(
                color = if (isSelected) color else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) color else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}