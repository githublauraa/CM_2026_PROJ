package com.example.voxel_review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

/**
 * Actividad principal y punto de entrada de la aplicación.
 * Configura el contenido de Jetpack Compose y aplica el tema de Voxel Review.
 */
class MainActivity : ComponentActivity() {

    /**
     * Inicializa la interfaz principal de la aplicación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Voxel_ReviewTheme {
                VoxelReviewApp()
            }
        }
    }
}