package com.pascal.xpense.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pascal.xpense.ui.component.screenUtils.TopAppBarComponent
import com.pascal.xpense.ui.screen.chat.component.ChatBubble
import com.pascal.xpense.ui.screen.chat.component.ChatInputBar
import com.pascal.xpense.ui.screen.chat.state.ChatUIState
import com.pascal.xpense.ui.screen.chat.state.LocalChatEvent
import com.pascal.xpense.ui.theme.CoralExpense
import com.pascal.xpense.ui.theme.DeepNavy
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.ai_assistant

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    uiState: ChatUIState = ChatUIState()
) {
    val event = LocalChatEvent.current
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size, uiState.isLoading) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.lastIndex)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBarComponent(
            title = stringResource(Res.string.ai_assistant),
            color = DeepNavy
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.messages, key = { it.id }) { message ->
                ChatBubble(message = message)
            }

            if (uiState.isLoading) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(12.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = DeepNavy
                            )
                        }
                    }
                }
            }
        }

        if (uiState.error != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CoralExpense.copy(alpha = 0.15f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = CoralExpense,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = uiState.error,
                    style = MaterialTheme.typography.bodySmall,
                    color = CoralExpense
                )
            }
        }

        ChatInputBar(
            input = uiState.input,
            isLoading = uiState.isLoading,
            pendingImage = uiState.pendingImage,
            onInputChange = event.onInputChange,
            onAttachClick = event.onAttachClick,
            onSend = event.onSend,
            onClearImage = event.onClearImage
        )
    }
}
