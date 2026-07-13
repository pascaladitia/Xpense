package com.pascal.xpense.ui.screen.addtransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pascal.xpense.ui.component.screenUtils.TopAppBarComponent
import com.pascal.xpense.ui.screen.addtransaction.state.AddTransactionUIState
import com.pascal.xpense.ui.screen.addtransaction.state.LocalAddTransactionEvent
import com.pascal.xpense.ui.theme.CoralExpense
import com.pascal.xpense.ui.theme.DeepNavy
import com.pascal.xpense.ui.theme.GreenIncome

@Composable
fun AddTransactionScreen(
    uiState: AddTransactionUIState
) {
    val event = LocalAddTransactionEvent.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        TopAppBarComponent(
            leftIcon1 = Icons.AutoMirrored.Filled.ArrowBack,
            onLeftIcon1Click = event.onCancel,
            title = "Add Transaction"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            PageTitle()

            Spacer(modifier = Modifier.height(24.dp))

            AmountSection(
                value = uiState.amount,
                onValueChange = event.onAmountChange,
                isError = uiState.amountError
            )

            Spacer(modifier = Modifier.height(20.dp))

            FormInput(
                label = "Title",
                value = uiState.title,
                onValueChange = event.onTitleChange,
                isError = uiState.titleError,
                placeholder = "What is this for?",
                trailingIcon = { Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DateField(
                label = "Date",
                value = uiState.date,
                isError = uiState.dateError,
                onClick = event.onDateClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            TypeSection(selectedType = uiState.type, onTypeSelected = event.onTypeChange)

            Spacer(modifier = Modifier.height(16.dp))

            CategorySection(
                selectedType = uiState.type,
                selectedCategory = uiState.category,
                onCategorySelected = event.onCategoryChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            AttachmentField(
                hasAttachment = uiState.attachmentPath != null,
                onClick = event.onAttachmentClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            SaveButton(
                isSaving = uiState.isSaving,
                onClick = event.onSave
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PageTitle() {
    Text(
        text = "New Transaction",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Enter the details of your transaction",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun AmountSection(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Amount",
            style = MaterialTheme.typography.labelSmall,
            color = if (isError) CoralExpense else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.displaySmall.copy(
                color = if (isError) CoralExpense else MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            cursorBrush = SolidColor(if (isError) CoralExpense else DeepNavy),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isError) CoralExpense else MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    innerTextField()
                }
            }
        )
        if (isError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Please enter a valid amount",
                style = MaterialTheme.typography.labelSmall,
                color = CoralExpense
            )
        }
    }
}

@Composable
private fun FormInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    placeholder: String = "",
    trailingIcon: @Composable () -> Unit = {}
) {
    Column {
        Row {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (isError) CoralExpense else MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (isError) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(required)",
                    style = MaterialTheme.typography.labelSmall,
                    color = CoralExpense
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = if (isError) CoralExpense else MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box {
                            if (value.isEmpty() && placeholder.isNotEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                trailingIcon()
            }
        }
    }
}

@Composable
private fun DateField(
    label: String,
    value: String,
    isError: Boolean,
    onClick: () -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = if (isError) CoralExpense else MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (isError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date is required",
                style = MaterialTheme.typography.labelSmall,
                color = CoralExpense
            )
        }
    }
}

@Composable
private fun TypeSection(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Type",
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
                label = "Expense",
                isSelected = selectedType == "EXPENSE",
                color = CoralExpense,
                onClick = { onTypeSelected("EXPENSE") }
            )
            TypeOption(
                modifier = Modifier.weight(1f),
                label = "Income",
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

@Composable
private fun CategorySection(
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
            "Investment" to Icons.Default.TrendingUp,
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

@Composable
private fun AttachmentField(
    hasAttachment: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (hasAttachment) DeepNavy else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (hasAttachment) "Receipt attached" else "Attach Receipt Photo",
                style = MaterialTheme.typography.bodyMedium,
                color = if (hasAttachment) DeepNavy else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SaveButton(
    isSaving: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isSaving,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
    ) {
        Text(
            text = if (isSaving) "Saving..." else "Save Transaction",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
