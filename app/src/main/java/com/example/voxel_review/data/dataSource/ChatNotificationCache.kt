package com.example.voxel_review.data.dataSource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Singleton

data class CachedChatNotification(val id: String, val senderId: String, val receivedAt: Long)

@Singleton
class ChatNotificationCache @Inject constructor(@param:ApplicationContext context: Context) {
    private val preferences = context.getSharedPreferences("chat_notification_history", Context.MODE_PRIVATE)
    private val changes = MutableStateFlow(0)

    fun observe(userId: String) = changes.map { read(userId) }

    @Synchronized
    fun save(userId: String, id: String, senderId: String, receivedAt: Long) {
        val stored = read(userId)
        if (stored.any { it.id == id }) return

        val latest = (listOf(CachedChatNotification(id, senderId, receivedAt)) + stored)
            .sortedByDescending { it.receivedAt }
            .take(30)
        val data = JSONArray()
        latest.forEach { notice ->
            data.put(JSONObject().apply {
                put("id", notice.id)
                put("senderId", notice.senderId)
                put("receivedAt", notice.receivedAt)
            })
        }
        preferences.edit().putString(userId, data.toString()).apply()
        changes.update { it + 1 }
    }

    private fun read(userId: String): List<CachedChatNotification> {
        val stored = preferences.getString(userId, null) ?: return emptyList()
        return try {
            val data = JSONArray(stored)
            List(data.length()) { index ->
                val item = data.getJSONObject(index)
                CachedChatNotification(
                    id = item.getString("id"),
                    senderId = item.getString("senderId"),
                    receivedAt = item.getLong("receivedAt")
                )
            }
        } catch (_: JSONException) {
            emptyList()
        }
    }
}
