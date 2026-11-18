package com.example.voxel_review.ui.screens.chat.conversation.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.chat.ChatContact
import com.example.voxel_review.data.info.chat.ChatMessage
import com.example.voxel_review.ui.screens.chat.components.ChatAvatar
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ChatMessageBubble(
    message: ChatMessage,
    loadPhoto: suspend (String) -> Result<Bitmap>,
    modifier: Modifier = Modifier,
    contact: ChatContact? = null
) {
    val formatter = remember {
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault())
    }
    val bubbleShape = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = if (message.isMine) 20.dp else 6.dp,
        bottomEnd = if (message.isMine) 6.dp else 20.dp
    )
    val bubbleColor = if (message.isMine) Color(0xFFC5F6FF) else Color(0xFFECE5FF)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isMine) {
            if (contact == null) {
                Spacer(Modifier.width(28.dp))
            } else {
                ChatAvatar(contact = contact, size = 28)
            }
            Spacer(Modifier.width(8.dp))
        }
        Column(
            modifier = Modifier.widthIn(max = 270.dp)
                .background(bubbleColor, bubbleShape)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            if (message.photoPath != null && !message.isSending) {
                ChatPhotoMessage(path = message.photoPath, loadPhoto = loadPhoto)
            } else {
                Text(
                    text = message.text,
                    color = Color(0xFF16182D),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Text(
                text = if (message.isSending) "Enviando…" else formatter.format(message.sentAt),
                style = MaterialTheme.typography.labelSmall,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFF45465B),
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
            )
        }
    }
}
