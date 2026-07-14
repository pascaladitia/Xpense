package com.pascal.xpense.ui.screen.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.component.screenUtils.PhotoPickerSheet
import com.pascal.xpense.ui.screen.chat.state.ChatEvent
import com.pascal.xpense.ui.screen.chat.state.LocalChatEvent
import com.pascal.xpense.utils.rememberCameraCapture
import com.pascal.xpense.utils.rememberImagePicker
import com.pascal.xpense.utils.showToast
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = koinInject<ChatViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showPhotoPicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val permissionsController = rememberPermissionsControllerFactory().createPermissionsController()
    BindEffect(permissionsController)

    val cameraCapture = rememberCameraCapture { bytes, name ->
        if (bytes != null) viewModel.setImage(bytes, name)
        showPhotoPicker = false
    }
    val imagePicker = rememberImagePicker { bytes, name ->
        if (bytes != null) viewModel.setImage(bytes, name)
        showPhotoPicker = false
    }

    val event = remember {
        ChatEvent(
            onInputChange = { viewModel.updateInput(it) },
            onSend = { viewModel.send() },
            onAttachClick = {
                showToast("this feature coming soon..")
                // showPhotoPicker = true
            },
            onImagePicked = { bytes, name ->
                if (bytes != null) viewModel.setImage(bytes, name)
            },
            onClearImage = { viewModel.clearImage() },
            onDismissSheet = { showPhotoPicker = false }
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
        LocalChatEvent provides event
    ) {
        ChatScreen(uiState = uiState)
    }
}
