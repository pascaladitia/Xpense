package com.pascal.xpense.ui.screen.addtransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.component.screenUtils.TopAppBarComponent
import com.pascal.xpense.ui.screen.addtransaction.component.AmountSection
import com.pascal.xpense.ui.screen.addtransaction.component.AttachmentField
import com.pascal.xpense.ui.screen.addtransaction.component.CategorySection
import com.pascal.xpense.ui.screen.addtransaction.component.DateField
import com.pascal.xpense.ui.screen.addtransaction.component.FormInput
import com.pascal.xpense.ui.screen.addtransaction.component.PageTitle
import com.pascal.xpense.ui.screen.addtransaction.component.SaveButton
import com.pascal.xpense.ui.screen.addtransaction.component.TypeSection
import com.pascal.xpense.ui.screen.addtransaction.state.AddTransactionUIState
import com.pascal.xpense.ui.screen.addtransaction.state.LocalAddTransactionEvent
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.add_transaction
import xpense.sharedui.generated.resources.date
import xpense.sharedui.generated.resources.title_hint
import xpense.sharedui.generated.resources.transaction_title

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
            title = stringResource(Res.string.add_transaction)
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
                label = stringResource(Res.string.transaction_title),
                value = uiState.title,
                onValueChange = event.onTitleChange,
                isError = uiState.titleError,
                placeholder = stringResource(Res.string.title_hint),
                trailingIcon = { Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DateField(
                label = stringResource(Res.string.date),
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
                attachmentBytes = uiState.attachmentBytes,
                onClick = event.onAttachmentClick,
                onClear = event.onClearAttachment
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
