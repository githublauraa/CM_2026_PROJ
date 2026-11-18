package com.example.voxel_review.ui.screens.chat.inbox

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.chat.inbox.components.ChatConversationCard
import com.example.voxel_review.ui.screens.chat.components.ChatHistoryButton
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelBackgroundDark
import com.example.voxel_review.ui.utils.ContentStatus
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun ChatInboxContent(
    state: ChatInboxState,
    onBack: () -> Unit,
    onChatClick: (String) -> Unit,
    onRetry: () -> Unit,
    onLoadOlder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = VoxelBackground,
        topBar = { ScreenHeader(stringResource(R.string.nav_chats), onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
                .background(Brush.verticalGradient(listOf(VoxelBackground, VoxelBackgroundDark))),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp)
        ) {
            when {
                state.error != null && state.conversations.isEmpty() -> item {
                    ContentStatus(state.error, onRetry = onRetry)
                }
                state.isLoading && state.conversations.isEmpty() -> item {
                    ContentStatus(stringResource(R.string.chat_inbox_loading), loading = true)
                }
                state.conversations.isEmpty() -> item {
                    ContentStatus(stringResource(R.string.chat_inbox_empty))
                }
            }

            itemsIndexed(state.conversations, key = { _, conversation -> conversation.id }) { index, conversation ->
                val first = index == 0
                val last = index == state.conversations.lastIndex
                val topCorner = if (first) 24.dp else 0.dp
                val bottomCorner = if (last) 24.dp else 0.dp

                ChatConversationCard(
                    conversation = conversation,
                    onClick = { onChatClick(conversation.contact.id) },
                    shape = RoundedCornerShape(topCorner, topCorner, bottomCorner, bottomCorner),
                    showDivider = !last
                )
            }

            if (state.nextCursor != null) {
                item {
                    ChatHistoryButton(
                        label = stringResource(R.string.chat_inbox_older),
                        isLoading = state.isLoadingOlder,
                        error = state.olderError,
                        onClick = onLoadOlder
                    )
                }
            }
        }
    }
}
