package com.pascal.xpense.ui.screen.addtransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.component.screenUtils.DatePickerComponent
import com.pascal.xpense.ui.component.screenUtils.PhotoPickerSheet
import com.pascal.xpense.ui.screen.addtransaction.state.AddTransactionEvent
import com.pascal.xpense.ui.screen.addtransaction.state.LocalAddTransactionEvent
import com.pascal.xpense.utils.rememberCameraCapture
import com.pascal.xpense.utils.rememberImagePicker
import com.pascal.xpense.utils.saveImageBytesToFile
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AddTransactionRoute(
    viewModel: AddTransactionViewModel = koinInject<AddTransactionViewModel>(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val permissionsController = rememberPermissionsControllerFactory().createPermissionsController()
    BindEffect(permissionsController)

    val cameraCapture = rememberCameraCapture { bytes, name ->
        val path = if (bytes != null) saveImageBytesToFile(bytes, name) else null
        viewModel.setAttachment(path, bytes)
        showPhotoPicker = false
    }
    val imagePicker = rememberImagePicker { bytes, name ->
        val path = if (bytes != null) saveImageBytesToFile(bytes, name) else null
        viewModel.setAttachment(path, bytes)
        showPhotoPicker = false
    }

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
            onAttachmentClick = { showPhotoPicker = true },
            onClearAttachment = { viewModel.clearAttachment() }
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
            onCameraClick = {
                coroutineScope.launch {
                    try {
                        permissionsController.providePermission(Permission.CAMERA)
                        cameraCapture.launch()
                    } catch (_: Exception) { }
                }
            },
            onGalleryClick = { imagePicker.launch() },
            onDismiss = { showPhotoPicker = false }
        )
    }

    CompositionLocalProvider(
        LocalAddTransactionEvent provides event
    ) {
        AddTransactionScreen(uiState = uiState)
    }
}
