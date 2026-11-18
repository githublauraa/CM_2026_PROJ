package com.example.voxel_review.data.dataSource.services

import com.example.voxel_review.data.info.chat.ChatCursor
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatFirestoreService @Inject constructor(private val db: FirebaseFirestore) {

    fun notifications(userId: String): Query =
        db.collection("chatNotifications").document(userId).collection("messages")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(30)

    fun conversations(userId: String, limit: Long, cursor: ChatCursor? = null): Query {
        val query = db.collection("chats")
            .whereArrayContains("participantIds", userId)
        return pageQuery(query, "updatedAt", limit, cursor)
    }

    fun messages(chatId: String, limit: Long, cursor: ChatCursor? = null): Query {
        val query = db.collection("chats").document(chatId).collection("messages")
        return pageQuery(query, "sentAt", limit, cursor)
    }

    private fun pageQuery(query: Query, dateField: String, limit: Long, cursor: ChatCursor?): Query {
        val ordered = query.orderBy(dateField, Query.Direction.DESCENDING)
            .orderBy(FieldPath.documentId(), Query.Direction.DESCENDING)
        if (cursor == null) return ordered.limit(limit)

        val timestamp = Timestamp(cursor.timestamp.epochSecond, cursor.timestamp.nano)
        return ordered.startAfter(timestamp, cursor.id).limit(limit)
    }

    fun observe(query: Query): Flow<QuerySnapshot> = callbackFlow {
        val listener = query.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
            if (error != null) {
                close(error)
            } else if (snapshot != null) {
                trySend(snapshot)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun getPage(query: Query): QuerySnapshot = query.get(Source.SERVER).await()

    suspend fun getContact(userId: String): DocumentSnapshot =
        db.collection("users").document(userId).get().await()

    fun newMessageId(chatId: String): String =
        db.collection("chats").document(chatId).collection("messages").document().id

    suspend fun sendMessage(
        chatId: String,
        participantIds: List<String>,
        senderId: String,
        text: String,
        messageId: String = newMessageId(chatId),
        photoPath: String? = null
    ) {
        val chat = db.collection("chats").document(chatId)
        val message = chat.collection("messages").document(messageId)
        val fields = mutableMapOf<String, Any>(
            "senderId" to senderId,
            "text" to text,
            "sentAt" to FieldValue.serverTimestamp()
        )
        if (photoPath != null) fields["photoPath"] = photoPath

        // El mensaje y la vista previa se confirman en la misma escritura.
        db.runBatch { batch ->
            batch.set(chat, mapOf(
                "participantIds" to participantIds,
                "lastMessage" to text,
                "lastMessageId" to message.id,
                "updatedAt" to FieldValue.serverTimestamp()
            ))
            batch.set(message, fields)
        }.await()
    }
}
