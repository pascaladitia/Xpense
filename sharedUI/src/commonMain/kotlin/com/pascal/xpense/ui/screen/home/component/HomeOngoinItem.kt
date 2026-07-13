package com.pascal.xpense.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.label_episode
import com.pascal.xpense.domain.model.AnimeItem
import com.pascal.xpense.ui.component.screenUtils.DynamicAsyncImage
import com.pascal.xpense.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeOngoingItem(
    modifier: Modifier = Modifier,
    items: AnimeItem? = null
) {
    if (items == null) return

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        DynamicAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .height(260.dp)
                .background(Color.Gray),
            imageUrl = items.poster,
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = items.title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.label_episode, items.episodes),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.StarRate,
                contentDescription = null,
                tint = Color.Yellow
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = items.score,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeOngoingPreview() {
    AppTheme {
        HomeOngoingItem()
    }
}