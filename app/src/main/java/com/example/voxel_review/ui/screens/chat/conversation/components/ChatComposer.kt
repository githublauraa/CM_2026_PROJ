package com.example.voxel_review.ui.screens.chat.conversation.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelBackgroundDark
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface

@Composable
fun ChatComposer(
    draft: String,
    canWrite: Boolean,
    isSending: Boolean,
    error: String?,
    onDraftChange: (String) -> Unit,
    onSend: () -> Unit,
    onPhotoCaptured: (Uri) -> Unit,
    onCameraError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().background(VoxelSurface)) {
        HorizontalDivider(color = VoxelBorder)
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFFF4F0FF), RoundedCornerShape(26.dp))
                    .border(1.dp, Color(0xFFD6CBEA), RoundedCornerShape(26.dp))
                    .padding(end = 6.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ChatCameraButton(
                    enabled = canWrite && !isSending,
                    onPhotoCaptured = onPhotoCaptured,
                    onError = onCameraError,
                    modifier = Modifier.padding(start = 3.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = draft,
                    onValueChange = onDraftChange,
                    enabled = canWrite && !isSending,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily.SansSerif),
                    placeholder = {
                        Text(
                            text = if (isSending) stringResource(R.string.sending) else stringResource(R.string.message_placeholder),
                            color = Color(0xFF525268),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp
                        )
                    },
                    maxLines = 4,
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = VoxelBackgroundDark,
                        unfocusedTextColor = VoxelBackgroundDark,
                        disabledTextColor = VoxelBackgroundDark,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        cursorColor = VoxelBackgroundDark
                    ),
                    modifier = Modifier.weight(1f).testTag("chatDraft")
                )
                FilledIconButton(
                    onClick = onSend,
                    enabled = canWrite && !isSending && draft.isNotBlank(),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = VoxelSurface,
                        contentColor = VoxelPrimary,
                        disabledContainerColor = Color(0xFFDED8EE),
                        disabledContentColor = Color(0xFF6A617E)
                    ),
                    modifier = Modifier.padding(bottom = 4.dp).size(48.dp).testTag("sendChatMessage")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = stringResource(R.string.send_message))
                }
            }
        }
    }
}
