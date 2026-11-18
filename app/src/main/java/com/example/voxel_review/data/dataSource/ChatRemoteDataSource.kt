package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.ChatContactDto
import com.example.voxel_review.data.dtos.ChatDto
import com.example.voxel_review.data.dtos.ChatMessageDto
import com.example.voxel_review.data.dtos.ChatNotificationDto
import com.example.voxel_review.data.info.chat.ChatCursor
import com.example.voxel_review.data.info.chat.ChatPage
import kotlinx.coroutines.flow.Flow

data class ChatDocument<T>(
    val id: String,
    val data: T,
    val hasPendingWrites: Boolean
)

interface ChatRemoteDataSource {
    fun observeNotifications(userId: String): Flow<List<ChatNotificationDto>>
    fun observeConversations(userId: String): Flow<ChatPage<ChatDocument<ChatDto>>>
    suspend fun getConversationsBefore(userId: String, cursor: ChatCursor): ChatPage<ChatDocument<ChatDto>>
    fun observeMessages(chatId: String): Flow<ChatPage<ChatDocument<ChatMessageDto>>>
    suspend fun getMessagesBefore(chatId: String, cursor: ChatCursor): ChatPage<ChatDocument<ChatMessageDto>>
    suspend fun getContact(userId: String): ChatContactDto?
    suspend fun sendMessage(chatId: String, participantIds: List<String>, senderId: String, text: String)
    fun newMessageId(chatId: String): String
    suspend fun sendPhotoMessage(chatId: String, participantIds: List<String>, senderId: String, messageId: String, photoPath: String)
}
