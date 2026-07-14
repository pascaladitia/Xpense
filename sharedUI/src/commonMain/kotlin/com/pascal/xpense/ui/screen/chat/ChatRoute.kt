package com.pascal.xpense.ui.screen.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.xpense.ui.component.screenUtils.PhotoPickerSheet
import com.pascal.xpense.ui.screen.chat.state.ChatEvent
import com.pascal.xpense.ui.screen.chat.state.LocalChatEvent
import org.koin.compose.koinInject

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = koinInject<ChatViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showPhotoPicker by remember { mutableStateOf(false) }

    val event = remember {
        ChatEvent(
            onInputChange = { viewModel.updateInput(it) },
            onSend = { viewModel.send() },
            onAttachClick = { showPhotoPicker = true },
            onImagePicked = { bytes, name ->
                if (bytes != null) viewModel.setImage(bytes, name)
            },
            onClearImage = { viewModel.clearImage() },
            onDismissSheet = { showPhotoPicker = false }
        )
    }

    if (showPhotoPicker) {
        PhotoPickerSheet(
            onPhotoSelected = { bytes, name ->
                if (bytes != null) viewModel.setImage(bytes, name)
            },
            onDismiss = { showPhotoPicker = false }
        )
    }

    CompositionLocalProvider(
        LocalChatEvent provides event
    ) {
        ChatScreen(uiState = uiState)
    }
}
