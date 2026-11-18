package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.ChatDocument
import com.example.voxel_review.data.dataSource.ChatRemoteDataSource
import com.example.voxel_review.data.dataSource.services.ChatFirestoreService
import com.example.voxel_review.data.dtos.ChatContactDto
import com.example.voxel_review.data.dtos.ChatDto
import com.example.voxel_review.data.dtos.ChatMessageDto
import com.example.voxel_review.data.dtos.ChatNotificationDto
import com.example.voxel_review.data.info.chat.ChatCursor
import com.example.voxel_review.data.info.chat.ChatPage
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
import com.google.firebase.firestore.QuerySnapshot
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import java.time.Instant

class ChatFirestoreDataSourceImpl @Inject constructor(
    private val service: ChatFirestoreService
) : ChatRemoteDataSource {

    override fun observeNotifications(userId: String) =
        service.observe(service.notifications(userId)).map { snapshot ->
            snapshot.documents.mapNotNull { document ->
                document.toObject(ChatNotificationDto::class.java)?.copy(id = document.id)
            }
        }

    override fun observeConversations(userId: String) =
        service.observe(service.conversations(userId, PAGE_LIMIT)).map { it.toChatPage() }

    override suspend fun getConversationsBefore(userId: String, cursor: ChatCursor) =
        service.getPage(service.conversations(userId, PAGE_LIMIT, cursor)).toChatPage()

    override fun observeMessages(chatId: String) =
        service.observe(service.messages(chatId, PAGE_LIMIT)).map { it.toMessagePage() }

    override suspend fun getMessagesBefore(chatId: String, cursor: ChatCursor) =
        service.getPage(service.messages(chatId, PAGE_LIMIT, cursor)).toMessagePage()

    override suspend fun getContact(userId: String): ChatContactDto? =
        service.getContact(userId).toObject(ChatContactDto::class.java)

    override suspend fun sendMessage(
        chatId: String,
        participantIds: List<String>,
        senderId: String,
        text: String
    ) {
        service.sendMessage(chatId, participantIds, senderId, text)
    }

    override fun newMessageId(chatId: String): String = service.newMessageId(chatId)

    override suspend fun sendPhotoMessage(
        chatId: String,
        participantIds: List<String>,
        senderId: String,
        messageId: String,
        photoPath: String
    ) {
        service.sendMessage(chatId, participantIds, senderId, "Foto", messageId, photoPath)
    }

    private fun QuerySnapshot.toChatPage() = toPage(ChatDto::class.java) { it.updatedAt }

    private fun QuerySnapshot.toMessagePage() = toPage(ChatMessageDto::class.java) { it.sentAt }

    private fun <T : Any> QuerySnapshot.toPage(
        dtoClass: Class<T>,
        timestampOf: (T) -> Timestamp?
    ): ChatPage<ChatDocument<T>> {
        val entries = documents.mapNotNull { document ->
            val dto = document.toObject(dtoClass, ESTIMATE) ?: return@mapNotNull null
            ChatDocument(document.id, dto, document.metadata.hasPendingWrites())
        }
        val items = entries.take(PAGE_SIZE)
        val lastConfirmed = items.lastOrNull { !it.hasPendingWrites }
        val nextCursor = if (size() > PAGE_SIZE && lastConfirmed != null) {
            timestampOf(lastConfirmed.data)?.let { timestamp ->
                ChatCursor(lastConfirmed.id, Instant.ofEpochSecond(timestamp.seconds, timestamp.nanoseconds.toLong()))
            }
        } else {
            null
        }
        return ChatPage(items, nextCursor)
    }

    private companion object {
        const val PAGE_SIZE = 30
        const val PAGE_LIMIT = 31L
    }
}
