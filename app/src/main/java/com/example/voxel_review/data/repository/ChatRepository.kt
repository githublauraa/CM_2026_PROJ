package com.example.voxel_review.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.voxel_review.data.dataSource.ChatDocument
import com.example.voxel_review.data.dataSource.ChatRemoteDataSource
import com.example.voxel_review.data.dataSource.services.ChatPhotoStorageService
import com.example.voxel_review.data.dtos.ChatDto
import com.example.voxel_review.data.dtos.ChatMessageDto
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
import com.example.voxel_review.data.info.chat.CHAT_MESSAGE_LIMIT
import com.example.voxel_review.data.info.chat.ChatContact
import com.example.voxel_review.data.info.chat.ChatConversation
import com.example.voxel_review.data.info.chat.ChatCursor
import com.example.voxel_review.data.info.chat.ChatMessage
import com.example.voxel_review.data.info.chat.ChatPage
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.Instant

class ChatRepository @Inject constructor(
    private val remoteDataSource: ChatRemoteDataSource,
    private val authRepository: AuthRepository,
    private val photoStorage: ChatPhotoStorageService
) {
    fun observeNotifications(userId: String): Flow<Result<List<NotificationInfo>>> = flow {
        remoteDataSource.observeNotifications(userId).collect { notices ->
            val notifications = notices.mapNotNull { notice ->
                if (notice.senderId.isBlank()) return@mapNotNull null

                NotificationInfo(
                    id = notice.id,
                    title = notice.senderName.ifBlank { "Usuario" },
                    message = if (notice.isPhoto) "Te envió una foto" else "Te envió un mensaje",
                    imageUrl = notice.senderPhotoUrl,
                    chatSenderId = notice.senderId
                )
            }
            emit(Result.success(notifications))
        }
    }.catch { error ->
        if (error is CancellationException || error !is Exception) throw error
        emit(Result.failure(Exception("No se pudieron cargar los avisos de mensajes.", error)))
    }

    fun observeConversations(): Flow<Result<ChatPage<ChatConversation>>> = flow {
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            emit(Result.failure(Exception("Inicia sesión para ver tus chats.")))
            return@flow
        }

        val contacts = mutableMapOf<String, ChatContact>()
        remoteDataSource.observeConversations(userId).collect { page ->
            emit(Result.success(toConversations(page, userId, contacts)))
        }
    }.catch { error ->
        if (error is CancellationException || error !is Exception) throw error
        emit(Result.failure(Exception("No se pudieron cargar tus chats. Inténtalo de nuevo.", error)))
    }

    suspend fun loadOlderConversations(cursor: ChatCursor): Result<ChatPage<ChatConversation>> {
        return try {
            val userId = authRepository.currentUser?.uid
                ?: return Result.failure(Exception("Inicia sesión para ver tus chats."))
            val page = remoteDataSource.getConversationsBefore(userId, cursor)
            Result.success(toConversations(page, userId, mutableMapOf()))
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(Exception("No se pudieron cargar más chats. Inténtalo de nuevo.", error))
        }
    }

    suspend fun getContact(contactId: String): Result<ChatContact> {
        return try {
            val contact = remoteDataSource.getContact(contactId)
                ?: return Result.failure(Exception("El perfil de este usuario ya no está disponible."))
            Result.success(ChatContact(contactId, contact.username.ifBlank { "Usuario" }, contact.photoUrl.orEmpty()))
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo cargar el perfil del chat.", error))
        }
    }

    fun observeMessages(contactId: String): Flow<Result<ChatPage<ChatMessage>>> = flow {
        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            emit(Result.failure(Exception("Inicia sesión para ver los mensajes.")))
            return@flow
        }

        val chatId = participantIds(userId, contactId).joinToString("~")
        remoteDataSource.observeMessages(chatId).collect { page ->
            emit(Result.success(toMessages(page, userId)))
        }
    }.catch { error ->
        if (error is CancellationException || error !is Exception) throw error
        emit(Result.failure(Exception("No se pudieron cargar los mensajes. Inténtalo de nuevo.", error)))
    }

    suspend fun loadOlderMessages(contactId: String, cursor: ChatCursor): Result<ChatPage<ChatMessage>> {
        return try {
            val userId = authRepository.currentUser?.uid
                ?: return Result.failure(Exception("Inicia sesión para ver los mensajes."))
            val chatId = participantIds(userId, contactId).joinToString("~")
            val page = remoteDataSource.getMessagesBefore(chatId, cursor)
            Result.success(toMessages(page, userId))
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(Exception("No se pudieron cargar los mensajes anteriores.", error))
        }
    }

    suspend fun sendMessage(contactId: String, draft: String): Result<Unit> {
        val text = draft.trim()
        if (text.isEmpty() || text.length > CHAT_MESSAGE_LIMIT) {
            return Result.failure(Exception("Escribe un mensaje de 1 a $CHAT_MESSAGE_LIMIT caracteres."))
        }

        return try {
            val userId = authRepository.currentUser?.uid
                ?: return Result.failure(Exception("Inicia sesión para enviar mensajes."))
            val participants = participantIds(userId, contactId)
            remoteDataSource.sendMessage(participants.joinToString("~"), participants, userId, text)
            Result.success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo enviar el mensaje. Inténtalo de nuevo.", error))
        }
    }

    suspend fun sendPhoto(contactId: String, capture: Uri): Result<Unit> {
        return try {
            val userId = authRepository.currentUser?.uid
                ?: return Result.failure(Exception("Inicia sesión para enviar fotos."))
            val participants = participantIds(userId, contactId)
            val chatId = participants.joinToString("~")
            val messageId = remoteDataSource.newMessageId(chatId)
            val photoPath = "chatPhotos/$chatId/$userId/$messageId.jpg"
            photoStorage.upload(photoPath, capture)

            try {
                remoteDataSource.sendPhotoMessage(chatId, participants, userId, messageId, photoPath)
            } catch (error: CancellationException) {
                // La escritura en Firestore puede seguir pendiente tras cerrar la pantalla.
                throw error
            } catch (error: Exception) {
                try {
                    photoStorage.removeUpload(photoPath)
                } catch (cancellation: CancellationException) {
                    throw cancellation
                } catch (cleanupError: Exception) {
                    error.addSuppressed(cleanupError)
                }
                throw error
            }
            Result.success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo enviar la foto. Inténtalo de nuevo.", error))
        }
    }

    suspend fun loadPhoto(contactId: String, path: String): Result<Bitmap> {
        return try {
            val userId = authRepository.currentUser?.uid
                ?: return Result.failure(Exception("Inicia sesión para ver la foto."))
            val participants = participantIds(userId, contactId)
            val chatId = participants.joinToString("~")
            val validPath = participants.any { participant ->
                val prefix = "chatPhotos/$chatId/$participant/"
                path.startsWith(prefix) && path.removePrefix(prefix).matches(PHOTO_FILE_PATTERN)
            }
            require(validPath)
            Result.success(photoStorage.download(path))
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo cargar la foto.", error))
        }
    }

    fun discardCapture(capture: Uri): Result<Unit> {
        return try {
            photoStorage.discardCapture(capture)
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo borrar la foto temporal.", error))
        }
    }

    fun mergeConversations(
        current: List<ChatConversation>,
        received: List<ChatConversation>
    ): List<ChatConversation> {
        return (received + current)
            .distinctBy { it.id }
            .sortedWith(compareByDescending<ChatConversation> { it.updatedAt }.thenByDescending { it.id })
    }

    fun mergeMessages(current: List<ChatMessage>, received: List<ChatMessage>): List<ChatMessage> {
        return (received + current.filterNot { it.isSending })
            .distinctBy { it.id }
            .sortedWith(compareByDescending<ChatMessage> { it.sentAt }.thenByDescending { it.id })
    }

    private fun participantIds(userId: String, contactId: String): List<String> {
        require(userId != contactId && userId.matches(USER_ID_PATTERN) && contactId.matches(USER_ID_PATTERN))
        return listOf(userId, contactId).sorted()
    }

    private suspend fun toConversations(
        page: ChatPage<ChatDocument<ChatDto>>,
        userId: String,
        contacts: MutableMap<String, ChatContact>
    ): ChatPage<ChatConversation> {
        val contactIds = page.items.mapNotNull { chat ->
            chat.data.participantIds.firstOrNull { it != userId }
        }.toSet()
        // Solo se consultan los perfiles nuevos de esta página, por ID de documento.
        contacts.keys.retainAll(contactIds)
        val missing = contactIds - contacts.keys
        val loaded = coroutineScope {
            missing.map { contactId ->
                async {
                    getContact(contactId).getOrNull()
                }
            }.awaitAll()
        }
        contacts.putAll(loaded.filterNotNull().associateBy { it.id })

        val chats = page.items.mapNotNull { chat ->
            if (chat.hasPendingWrites) return@mapNotNull null
            val contactId = chat.data.participantIds.firstOrNull { it != userId } ?: return@mapNotNull null
            val contact = contacts[contactId] ?: ChatContact(contactId, "Usuario")
            val timestamp = chat.data.updatedAt ?: return@mapNotNull null
            ChatConversation(
                id = chat.id,
                contact = contact,
                lastMessage = chat.data.lastMessage,
                updatedAt = Instant.ofEpochSecond(timestamp.seconds, timestamp.nanoseconds.toLong())
            )
        }
        return ChatPage(chats, page.nextCursor)
    }

    private fun toMessages(
        page: ChatPage<ChatDocument<ChatMessageDto>>,
        userId: String
    ): ChatPage<ChatMessage> {
        val messages = page.items.mapNotNull { message ->
            val timestamp = message.data.sentAt ?: return@mapNotNull null
            ChatMessage(
                id = message.id,
                text = message.data.text,
                sentAt = Instant.ofEpochSecond(timestamp.seconds, timestamp.nanoseconds.toLong()),
                isMine = message.data.senderId == userId,
                isSending = message.hasPendingWrites,
                photoPath = message.data.photoPath
            )
        }
        return ChatPage(messages, page.nextCursor)
    }

    private companion object {
        val PHOTO_FILE_PATTERN = Regex("[A-Za-z0-9]{20}[.]jpg")
        val USER_ID_PATTERN = Regex("[A-Za-z0-9_-]{1,128}")
    }
}
