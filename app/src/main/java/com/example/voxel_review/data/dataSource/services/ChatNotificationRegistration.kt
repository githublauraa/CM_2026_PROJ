package com.example.voxel_review.data.dataSource.services

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class ChatNotificationRegistration @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var registration: Job? = null
    private var registeredDevice: Pair<String, String>? = null

    fun start() {
        auth.addAuthStateListener { refresh() }
    }

    fun refresh() {
        scope.launch {
            registration?.cancel()
            registration = launch { registerCurrentDevice() }
        }
    }

    private suspend fun registerCurrentDevice() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            registeredDevice = null
            return
        }
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) return

        try {
            val token = FirebaseMessaging.getInstance().token.await()
            val installationId = FirebaseInstallations.getInstance().id.await()
            val device = userId to token
            if (auth.currentUser?.uid != userId || registeredDevice == device) return

            firestore.collection("chatPushRegistrations").document(userId)
                .collection("devices").document(installationId)
                .set(mapOf("token" to token)).await()
            registeredDevice = device
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            Log.w("ChatNotifications", "No se pudo registrar el dispositivo para notificaciones.")
        }
    }
}
