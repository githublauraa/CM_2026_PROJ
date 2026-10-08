package com.example.voxel_review

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme
import dagger.hilt.android.AndroidEntryPoint
/**
 * Actividad principal y punto de entrada de la aplicación.
 * Configura el contenido de Jetpack Compose y aplica el tema de Voxel Review.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Permisos necesarios para hablar con la API local (10.0.2.2 / IP LAN).
    // Sin ellos, el sistema bloquea el tráfico TCP hacia la red local y Retrofit
    // recibe SocketTimeoutException (Local Network Protection de Android).
    private val requestLocalNetworkPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grants ->
            grants.forEach { (permission, granted) ->
                Log.d(TAG, "$permission concedido=$granted")
            }
        }

    /**
     * Inicializa la interfaz principal de la aplicación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        requestLocalNetworkIfNeeded()

        setContent {
            Voxel_ReviewTheme {
                VoxelReviewApp()
            }
        }
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
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}