package com.example.voxel_review

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.voxel_review.data.dataSource.services.ChatNotificationRegistration
import com.example.voxel_review.data.dataSource.services.ForegroundChatNotifications
import com.example.voxel_review.data.dataSource.services.MyFirebaseMessagingService
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import dagger.hilt.android.AndroidEntryPoint
/**
 * Actividad principal y punto de entrada de la aplicación.
 * Configura el contenido de Jetpack Compose y aplica el tema de Voxel Review.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var chatNotifications: ChatNotificationRegistration
    @Inject lateinit var foregroundNotifications: ForegroundChatNotifications
    private val chatNotification = MutableStateFlow<ChatNotificationTarget?>(null)

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) chatNotifications.refresh()
        }

    // Permisos necesarios para hablar con la API local (10.0.2.2 / IP LAN).
    // Sin ellos, el sistema bloquea el tráfico TCP hacia la red local y Retrofit
    // recibe SocketTimeoutException (Local Network Protection de Android).
    private val requestLocalNetworkPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grants ->
            grants.forEach { (permission, granted) ->
                Log.d(TAG, "$permission concedido=$granted")
            }
            requestNotificationsIfNeeded()
        }

    /**
     * Inicializa la interfaz principal de la aplicación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        readChatNotification(intent)

        requestLocalNetworkIfNeeded()

        setContent {
            Voxel_ReviewTheme {
                val target by chatNotification.collectAsStateWithLifecycle()
                VoxelReviewApp(
                    chatNotification = target,
                    foregroundMessages = foregroundNotifications.messages,
                    onForegroundChatOpened = { chatNotification.value = it },
                    onChatNotificationHandled = { chatNotification.value = null }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        foregroundNotifications.isAppResumed = true
        chatNotifications.refresh()
    }

    override fun onPause() {
        foregroundNotifications.isAppResumed = false
        super.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        readChatNotification(intent)
    }

    private fun readChatNotification(intent: Intent) {
        if (intent.action != MyFirebaseMessagingService.ACTION_OPEN_CHAT) return
        val recipientId = intent.getStringExtra(MyFirebaseMessagingService.EXTRA_RECIPIENT)
        val senderId = intent.getStringExtra(MyFirebaseMessagingService.EXTRA_SENDER)
        if (recipientId.isNullOrBlank() || senderId.isNullOrBlank()) return
        chatNotification.value = ChatNotificationTarget(recipientId, senderId)
        intent.action = null
    }

    /**
     * Solicita los permisos de red local si aún no están concedidos.
     */
    private fun requestLocalNetworkIfNeeded() {
        val pending = listOf(
            Manifest.permission.ACCESS_LOCAL_NETWORK,
            Manifest.permission.NEARBY_WIFI_DEVICES,
        ).filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }

        if (pending.isNotEmpty()) {
            requestLocalNetworkPermissions.launch(pending.toTypedArray())
        } else {
            requestNotificationsIfNeeded()
        }
    }

    private fun requestNotificationsIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

//SE USA PARA LAS PRUEBAS E2E
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        enableEdgeToEdge()
//
//        setContent {
//            Voxel_ReviewTheme {
//                VoxelReviewApp()
//            }
//        }
//    }
//}