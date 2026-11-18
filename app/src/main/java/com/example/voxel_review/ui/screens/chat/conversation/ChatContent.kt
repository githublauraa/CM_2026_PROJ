package com.example.voxel_review.ui.screens.chat.conversation

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.chat.conversation.components.ChatBackdrop
import com.example.voxel_review.ui.screens.chat.conversation.components.ChatComposer
import com.example.voxel_review.ui.screens.chat.conversation.components.ChatDayDivider
import com.example.voxel_review.ui.screens.chat.conversation.components.ChatHeader
import com.example.voxel_review.ui.screens.chat.conversation.components.ChatPhotoPreview
import com.example.voxel_review.ui.screens.chat.components.ChatHistoryButton
import com.example.voxel_review.ui.screens.chat.conversation.components.ChatMessageBubble
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.utils.ContentStatus
import java.time.ZoneId

@Composable
fun ChatContent(
    state: ChatState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onLoadOlder: () -> Unit,
    onDraftChange: (String) -> Unit,
    onSend: () -> Unit,
    onPhotoCaptured: (Uri) -> Unit,
    onCameraError: (String) -> Unit,
    onDiscardPhoto: () -> Unit,
    onSendPhoto: () -> Unit,
    loadPhoto: suspend (String) -> Result<Bitmap>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val timeZone = ZoneId.systemDefault()
    val latestMessage = state.messages.firstOrNull()
    LaunchedEffect(latestMessage?.id) {
        if (latestMessage != null && (listState.firstVisibleItemIndex <= 1 || latestMessage.isSending)) {
            listState.scrollToItem(0)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
        containerColor = VoxelBackground,
        topBar = { ChatHeader(contact = state.contact, onBack = onBack) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            val loadError = state.contactError ?: state.messagesError
            if (loadError != null) {
                ContentStatus(loadError, onRetry = onRetry)
            }

            Box(
                modifier = Modifier.weight(1f).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ChatBackdrop(Modifier.matchParentSize())

                if (state.messages.isEmpty() && loadError == null) {
                    val message = if (state.isLoading) "Cargando mensajes…" else "Envía el primer mensaje."
                    ContentStatus(message, loading = state.isLoading)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    reverseLayout = true,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(state.messages, key = { _, message -> message.id }) { index, message ->
                        val day = message.sentAt.atZone(timeZone).toLocalDate()
                        val olderMessage = state.messages.getOrNull(index + 1)
                        val olderDay = olderMessage?.sentAt?.atZone(timeZone)?.toLocalDate()
                        val startsDay = day != olderDay && (olderMessage != null || state.nextCursor == null)

                        val newerMessage = state.messages.getOrNull(index - 1)
                        val newerDay = newerMessage?.sentAt?.atZone(timeZone)?.toLocalDate()
                        val showAvatar = !message.isMine &&
                            (newerMessage == null || newerMessage.isMine || day != newerDay)

                        Column {
                            if (startsDay) ChatDayDivider(day)
                            ChatMessageBubble(
                                message = message,
                                loadPhoto = loadPhoto,
                                contact = if (showAvatar) state.contact else null
                            )
                        }
                    }

                    if (state.nextCursor != null) {
                        item {
                            ChatHistoryButton(
                                label = "Cargar mensajes anteriores",
                                isLoading = state.isLoadingOlder,
                                error = state.olderError,
                                onClick = onLoadOlder
                            )
                        }
                    }
                }
            }

            ChatComposer(
                draft = state.draft,
                canWrite = state.contact != null,
                isSending = state.isSending,
                error = if (state.photoDraft == null) state.sendError else null,
                onPhotoCaptured = onPhotoCaptured,
                onCameraError = onCameraError,
                onDraftChange = onDraftChange,
                onSend = onSend
            )
        }
    }

    state.photoDraft?.let { capture ->
        ChatPhotoPreview(
            photo = capture,
            isSending = state.isSending,
            error = state.sendError,
            onSend = onSendPhoto,
            onDiscard = onDiscardPhoto
        )
    }
}
