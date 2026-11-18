package com.example.voxel_review.ui.screens.chat.inbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ChatInboxScreen(
    viewModel: ChatInboxViewModel,
    onBack: () -> Unit,
    onChatClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ChatInboxContent(
        state = state,
        onBack = onBack,
        onChatClick = onChatClick,
        onRetry = viewModel::loadConversations,
        onLoadOlder = viewModel::loadOlderConversations,
        modifier = modifier
    )
}
