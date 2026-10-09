
package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.review.ReviewInfo

@Composable
fun ProfileReviewCard(
    review: ReviewInfo,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color(0xFF17122D),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {

        Text(
            text = review.tituloJuego,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = review.tituloDescripcion,
            color = Color.White
        )

        Text(
            text = review.descripcion,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Calificación general: ${review.ratingGeneral}",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onEditClick) {
                Text("Editar")
            }

            Button(onClick = onDeleteClick) {
                Text("Eliminar")
            }
        }
    }
}
