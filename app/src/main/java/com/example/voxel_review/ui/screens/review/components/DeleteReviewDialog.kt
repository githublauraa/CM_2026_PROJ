package com.example.voxel_review.ui.screens.review.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.backgroundDark
import com.example.voxel_review.ui.theme.errorDark

/**
 * Ventana de confirmación para eliminar una reseña.
 *
 * @param onConfirm Acción ejecutada al presionar "Aceptar": elimina la reseña.
 * @param onDismiss Acción ejecutada al presionar "Cancelar": cierra la ventana.
 */
@Composable
fun DeleteReviewDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,

        containerColor = backgroundDark,

        title = {
            Text(
                text = "Eliminar reseña",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        },

        text = {
            Text(
                text = "¿Estás seguro de que quieres eliminar esta reseña?",
                color = Color.White
            )
        },

        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Aceptar",
                    fontWeight = FontWeight.Bold,
                    color = errorDark
                )
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancelar",
                    color = Color.White
                )
            }
        }
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
fun DeleteReviewDialogPreview() {
    DeleteReviewDialog(
        onConfirm = {},
        onDismiss = {}
    )
}
