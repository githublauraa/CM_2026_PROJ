package com.example.voxel_review.data.info.chat

import java.time.Instant

data class ChatContact(
    val id: String,
    val name: String,
    val photoUrl: String = ""
)

data class ChatConversation(
    val id: String,
    val contact: ChatContact,
    val lastMessage: String,
    val updatedAt: Instant
)

data class ChatMessage(
    val id: String,
    val text: String,
    val sentAt: Instant,
    val isMine: Boolean,
    val isSending: Boolean,
    val photoPath: String? = null
)

data class ChatCursor(val id: String, val timestamp: Instant)

data class ChatPage<T>(val items: List<T>, val nextCursor: ChatCursor?)

const val CHAT_MESSAGE_LIMIT = 1000
