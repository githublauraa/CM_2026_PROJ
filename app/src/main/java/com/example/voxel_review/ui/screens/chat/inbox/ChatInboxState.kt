package com.example.voxel_review.ui.screens.chat.inbox

import com.example.voxel_review.data.info.chat.ChatConversation
import com.example.voxel_review.data.info.chat.ChatCursor

data class ChatInboxState(
    val conversations: List<ChatConversation> = emptyList(),
    val nextCursor: ChatCursor? = null,
    val isLoading: Boolean = true,
    val isLoadingOlder: Boolean = false,
    val error: String? = null,
    val olderError: String? = null
)
