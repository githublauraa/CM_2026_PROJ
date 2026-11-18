package com.example.voxel_review.ui.screens.chat.conversation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ChatContent(
        state = state,
        onBack = onBack,
        onRetry = viewModel::loadChat,
        onLoadOlder = viewModel::loadOlderMessages,
        onDraftChange = viewModel::updateDraft,
        onSend = viewModel::sendMessage,
        onPhotoCaptured = viewModel::onPhotoCaptured,
        onCameraError = viewModel::onCameraError,
        onDiscardPhoto = viewModel::discardPhoto,
        onSendPhoto = viewModel::sendPhoto,
        loadPhoto = viewModel::loadPhoto,
        modifier = modifier
    )
}
