package com.example.voxel_review.data.dtos

import com.google.firebase.Timestamp

data class ChatDto(
    val participantIds: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastMessageId: String = "",
    val updatedAt: Timestamp? = null
)

data class ChatMessageDto(
    val senderId: String = "",
    val text: String = "",
    val sentAt: Timestamp? = null,
    val photoPath: String? = null
)

data class ChatContactDto(
    val username: String = "",
    val photoUrl: String? = null
)

data class ChatNotificationDto(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val senderPhotoUrl: String = "",
    val isPhoto: Boolean = false,
    val createdAt: Timestamp? = null
)
