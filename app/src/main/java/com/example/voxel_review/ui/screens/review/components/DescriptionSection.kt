package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.voxel_review.ui.screens.GameDetail.components.LikeCard
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@Composable
fun DescriptionSection(
    titulo: String,
    descripcion: String,
    modifier: Modifier = Modifier,
    imagenUsuario: String = "",
    onAuthorClick: () -> Unit = {},
    onLikeClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VoxelSurface),
        border = BorderStroke(1.dp, VoxelBorder)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onAuthorClick),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AsyncImage(
                        model = imagenUsuario,
                        contentDescription = "Foto del autor",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(44.dp).clip(CircleShape)
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        Text(
                            fontFamily = FontFamily.SansSerif,
                            text = "RESEÑA DE LA COMUNIDAD",
                            color = VoxelPrimary,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            fontFamily = FontFamily.SansSerif,
                            text = titulo,
                            color = VoxelTextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                LikeCard(
                    isLiked = false,
                    likes = 2,
                    onLikeClick = onLikeClick
                )
            }
            HorizontalDivider(color = VoxelBorder)
            Text(
                fontFamily = FontFamily.SansSerif,
                text = descripcion.ifBlank { "Esta reseña aún no tiene una descripción." },
                color = VoxelTextPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

        }

    }
}

@Preview
@Composable
fun DescriptionSectionPreview() {
    DescriptionSection(
        titulo = "Reseña por nuevo",
        descripcion = "Una experiencia increíble con una excelente historia."
    )
}
