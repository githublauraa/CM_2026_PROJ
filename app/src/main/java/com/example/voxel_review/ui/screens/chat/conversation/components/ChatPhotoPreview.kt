package com.example.voxel_review.ui.screens.chat.conversation.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ChatPhotoPreview(
    photo: Uri,
    isSending: Boolean,
    error: String?,
    onSend: () -> Unit,
    onDiscard: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { if (!isSending) onDiscard() },
        title = { Text(stringResource(R.string.send_photo)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AsyncImage(
                    model = photo,
                    contentDescription = stringResource(R.string.photo_preview),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 160.dp, max = 300.dp)
                )
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            Button(onClick = onSend, enabled = !isSending) {
                Text(if (isSending) stringResource(R.string.sending) else stringResource(R.string.send_photo))
            }
        },
        dismissButton = {
            TextButton(onClick = onDiscard, enabled = !isSending) { Text(stringResource(R.string.discard)) }
        }
    )
}
