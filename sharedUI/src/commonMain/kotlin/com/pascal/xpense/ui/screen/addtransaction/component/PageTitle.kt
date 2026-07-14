package com.pascal.xpense.ui.screen.addtransaction.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.new_transaction
import xpense.sharedui.generated.resources.transaction_details_hint

@Composable
fun PageTitle() {
    Text(
        text = stringResource(Res.string.new_transaction),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(Res.string.transaction_details_hint),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}