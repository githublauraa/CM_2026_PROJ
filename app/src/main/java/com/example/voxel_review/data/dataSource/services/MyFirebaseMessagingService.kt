package com.example.voxel_review.data.dataSource.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.voxel_review.ChatNotificationTarget
import com.example.voxel_review.MainActivity
import com.example.voxel_review.R
import com.example.voxel_review.data.dataSource.ChatNotificationCache
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var registration: ChatNotificationRegistration
    @Inject lateinit var foregroundNotifications: ForegroundChatNotifications
    @Inject lateinit var notificationCache: ChatNotificationCache

    override fun onNewToken(token: String) {
        registration.refresh()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        if (data["type"] != "chat_message") return

        val recipientId = data["recipientId"] ?: return
        val senderId = data["senderId"] ?: return
        val chatId = data["chatId"] ?: return
        val messageId = data["messageId"] ?: return
        if (recipientId != auth.currentUser?.uid || recipientId == senderId) return

        val receivedAt = message.sentTime.takeIf { it > 0 } ?: System.currentTimeMillis()
        notificationCache.save(recipientId, "${chatId}_$messageId", senderId, receivedAt)

        val target = ChatNotificationTarget(recipientId, senderId)
        if (foregroundNotifications.showIfForeground(target)) return

        val localized = ContextCompat.getContextForLanguage(this)
        val manager = getSystemService(NotificationManager::class.java)
        if (!manager.areNotificationsEnabled()) return
        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                localized.getString(R.string.chat_notification_channel),
                NotificationManager.IMPORTANCE_HIGH
            )
        )

        val intent = Intent(this, MainActivity::class.java).apply {
            action = ACTION_OPEN_CHAT
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            setData(Uri.Builder().scheme("voxel-review").authority("chat").appendPath(chatId).build())
            putExtra(EXTRA_RECIPIENT, recipientId)
            putExtra(EXTRA_SENDER, senderId)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.campana_notificaciones)
            .setContentTitle(localized.getString(R.string.chat_notification_title))
            .setContentText(localized.getString(R.string.chat_notification_body))
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .build()

        // El mismo evento actualiza su aviso si Firestore lo vuelve a entregar.
        manager.notify("$chatId/$messageId", 0, notification)
    }

    companion object {
        const val ACTION_OPEN_CHAT = "com.example.voxel_review.OPEN_CHAT_NOTIFICATION"
        const val EXTRA_RECIPIENT = "chat_notification_recipient"
        const val EXTRA_SENDER = "chat_notification_sender"
        private const val CHANNEL_ID = "chat_messages"
    }
}
