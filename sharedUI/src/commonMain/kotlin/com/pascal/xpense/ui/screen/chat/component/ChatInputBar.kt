package com.pascal.xpense.ui.screen.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.pascal.xpense.ui.component.screenUtils.getAsyncImageLoader
import com.pascal.xpense.ui.screen.chat.state.PendingImage
import com.pascal.xpense.ui.theme.DeepNavy
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.attach_image_desc
import xpense.sharedui.generated.resources.message_placeholder
import xpense.sharedui.generated.resources.remove_image_desc
import xpense.sharedui.generated.resources.selected_image_desc
import xpense.sharedui.generated.resources.send_desc

@Composable
fun ChatInputBar(
    input: String,
    isLoading: Boolean,
    pendingImage: PendingImage?,
    onInputChange: (String) -> Unit,
    onAttachClick: () -> Unit,
    onSend: () -> Unit,
    onClearImage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        if (pendingImage != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(56.dp)) {
                    AsyncImage(
                        model = pendingImage.bytes,
                        imageLoader = getAsyncImageLoader(LocalPlatformContext.current),
                        contentDescription = stringResource(Res.string.selected_image_desc),
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    IconButton(
                        onClick = onClearImage,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.remove_image_desc),
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                Text(
                    text = pendingImage.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onAttachClick) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = stringResource(Res.string.attach_image_desc),
                    tint = DeepNavy
                )
            }

            TextField(
                value = input,
                onValueChange = onInputChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(Res.string.message_placeholder)) },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = false,
                maxLines = 4
            )

            IconButton(
                onClick = onSend,
                enabled = !isLoading && (input.isNotBlank() || pendingImage != null)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = stringResource(Res.string.send_desc),
                    tint = if (isLoading || (input.isBlank() && pendingImage == null)) Color.Gray else DeepNavy
                )
            }
        }
    }
}
