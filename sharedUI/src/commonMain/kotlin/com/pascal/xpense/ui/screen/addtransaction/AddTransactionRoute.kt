package com.pascal.xpense.ui.screen.addtransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.component.screenUtils.DatePickerComponent
import com.pascal.xpense.ui.component.screenUtils.PhotoPickerSheet
import com.pascal.xpense.ui.screen.addtransaction.state.AddTransactionEvent
import com.pascal.xpense.ui.screen.addtransaction.state.LocalAddTransactionEvent
import org.koin.compose.koinInject

@Composable
fun AddTransactionRoute(
    viewModel: AddTransactionViewModel = koinInject<AddTransactionViewModel>(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }

    val event = remember(showDatePicker, showPhotoPicker) {
        AddTransactionEvent(
            onTitleChange = { viewModel.updateTitle(it) },
            onAmountChange = { viewModel.updateAmount(it) },
            onDateChange = { viewModel.updateDate(it) },
            onTypeChange = { viewModel.updateType(it) },
            onCategoryChange = { viewModel.updateCategory(it) },
            onAttachmentChange = { viewModel.setAttachment(it) },
            onSave = { viewModel.save(onNavigateBack) },
            onCancel = onNavigateBack,
            onDateClick = { showDatePicker = true },
            onAttachmentClick = { showPhotoPicker = true }
        )
    }

    if (showDatePicker) {
        DatePickerComponent(
            show = true,
            onDismiss = { showDatePicker = false },
            onConfirm = { formattedDate, _ ->
                val parts = formattedDate.split("/")
                if (parts.size == 3) {
                    val day = parts[0]
                    val month = parts[1]
                    val year = "20${parts[2]}"
                    viewModel.updateDate("$year-$month-$day")
                }
                showDatePicker = false
            }
        )
    }

    if (showPhotoPicker) {
        PhotoPickerSheet(
            onPhotoSelected = { bytes, name ->
                viewModel.setAttachment(name)
            },
            onDismiss = { showPhotoPicker = false }
        )
    }

    CompositionLocalProvider(
        LocalAddTransactionEvent provides event
    ) {
        AddTransactionScreen(uiState = uiState)
    }
}
