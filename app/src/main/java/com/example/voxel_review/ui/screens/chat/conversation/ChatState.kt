package com.example.voxel_review.ui.screens.chat.conversation

import android.net.Uri
import com.example.voxel_review.data.info.chat.ChatContact
import com.example.voxel_review.data.info.chat.ChatCursor
import com.example.voxel_review.data.info.chat.ChatMessage

data class ChatState(
    val contact: ChatContact? = null,
    val messages: List<ChatMessage> = emptyList(),
    val nextCursor: ChatCursor? = null,
    val draft: String = "",
    val photoDraft: Uri? = null,
    val isLoading: Boolean = true,
    val isLoadingOlder: Boolean = false,
    val isSending: Boolean = false,
    val contactError: String? = null,
    val messagesError: String? = null,
    val olderError: String? = null,
    val sendError: String? = null
)
