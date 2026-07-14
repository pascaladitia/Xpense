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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.theme.DeepNavy

@Composable
fun CategorySection(
    selectedType: String,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = if (selectedType == "EXPENSE") {
        listOf(
            "Food" to Icons.Default.Restaurant,
            "Transport" to Icons.Default.DirectionsCar,
            "Shopping" to Icons.Default.ShoppingCart,
            "Bills" to Icons.Default.Receipt
        )
    } else {
        listOf(
            "Salary" to Icons.Default.AccountBalance,
            "Freelance" to Icons.Default.Computer,
            "Investment" to Icons.AutoMirrored.Filled.TrendingUp,
            "Gift" to Icons.Default.CardGiftcard
        )
    }

    Column {
        Text(
            text = "Category",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.take(2).forEach { (name, icon) ->
                CategoryChip(
                    modifier = Modifier.weight(1f),
                    icon = icon,
                    label = name,
                    isSelected = selectedCategory == name,
                    onClick = { onCategorySelected(name) }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.drop(2).forEach { (name, icon) ->
                CategoryChip(
                    modifier = Modifier.weight(1f),
                    icon = icon,
                    label = name,
                    isSelected = selectedCategory == name,
                    onClick = { onCategorySelected(name) }
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(72.dp)
            .background(
                color = if (isSelected) DeepNavy else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) DeepNavy else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) Color.White else DeepNavy,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}