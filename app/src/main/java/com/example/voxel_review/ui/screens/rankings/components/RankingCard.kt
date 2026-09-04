package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary

/**
 * Muestra la información de un usuario dentro del ranking.
 * Incluye su posición, imagen de perfil, nombre, cantidad de reseñas y porcentaje.
 *
 * @param posicion Posición que ocupa el usuario en el ranking.
 * @param nombre Nombre del usuario.
 * @param reseñas Cantidad de reseñas realizadas por el usuario.
 * @param porcentaje Porcentaje asociado al desempeño del usuario.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun RankingCard(
    posicion: String,
    nombre: String,
    reseñas: String,
    porcentaje: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = VoxelBorder
        ),
        colors = CardDefaults.cardColors(
            containerColor = VoxelSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Posición del usuario dentro del ranking.
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(
                        color = VoxelSurfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = posicion,
                    color = VoxelTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Image(
                painter = painterResource(
                    R.drawable.imagen_review_persona
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            // Información principal del usuario.
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = nombre,
                    color = VoxelTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = reseñas,
                    color = VoxelTextSecondary,
                    fontSize = 11.sp
                )
            }

            // Porcentaje destacado del usuario.
            Box(
                modifier = Modifier
                    .background(
                        color = VoxelSurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 6.dp
                    )
            ) {
                Text(
                    text = porcentaje,
                    color = VoxelPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Vista previa de una tarjeta del ranking.
 */
@Preview(showBackground = true)
@Composable
fun RankingCardPreview() {
    RankingCard(
        posicion = "2",
        nombre = "VoxelMaster",
        reseñas = "120 reseñas",
        porcentaje = "94%"
    )
}