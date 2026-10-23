package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.theme.*
import java.util.Locale

@Composable
fun FollowedReviewsSection(
    isLoading: Boolean,
    reviews: List<ReviewInfo>,
    onReviewClick: (ReviewInfo) -> Unit,
    onUserProfileClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (reviews.isEmpty()) {
        Text(
            text = "No hay publicaciones recientes de los usuarios que sigues.",
            color = VoxelTextSecondary,
            fontSize = 14.sp,
            modifier = modifier.padding(vertical = 20.dp)
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(reviews, key = { it.idResenia }) { review ->
                FollowedReviewCard(review, { onReviewClick(review) }, { onUserProfileClick(review.userId) })
            }
        }
    }
}

@Composable
private fun FollowedReviewCard(review: ReviewInfo, onClick: () -> Unit, onProfileClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VoxelSurface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(width = 64.dp, height = 84.dp)
                        .clip(RoundedCornerShape(8.dp)).background(VoxelSurfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Juego", color = VoxelTextSecondary, fontSize = 12.sp)
                    AsyncImage(
                        model = review.imagenJuego.takeIf { it.isNotBlank() },
                        contentDescription = "Portada de ${review.tituloJuego}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("RESEÑA DE VIDEOJUEGO", color = VoxelPrimary, fontSize = 10.sp)
                    Text(
                        review.tituloJuego.ifBlank { "Juego no disponible" },
                        color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                    if (review.desarrollador.isNotBlank()) {
                        Text(review.desarrollador, color = VoxelTextSecondary, fontSize = 12.sp)
                    }
                    Text("★ ${formatRating(review.ratingGeneral)}/5", color = VoxelAccentYellow, fontWeight = FontWeight.Bold)
                }
            }
            HorizontalDivider(color = VoxelBorder)
            Row(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).clickable(onClick = onProfileClick).padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.example.voxel_review.ui.utils.ProfileImage(profileImage = review.imagenUsuario, size = 32)
                Text(review.tituloDescripcion, color = White, fontSize = 13.sp, modifier = Modifier.weight(1f))
            }
            Text(
                review.descripcion, color = VoxelTextSecondary, fontSize = 14.sp,
                lineHeight = 21.sp, maxLines = 5, overflow = TextOverflow.Ellipsis
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RatingDetail("Jugabilidad", review.ratingJugabilidad, Modifier.weight(1f))
                RatingDetail("Gráficos", review.ratingGraficos, Modifier.weight(1f))
                RatingDetail("Historia", review.ratingHistoria, Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("${review.cantidadLikes} Me gusta", color = VoxelTextSecondary, fontSize = 12.sp)
                TextButton(onClick = onClick) { Text("Ver reseña", color = VoxelPrimary) }
            }
        }
    }
}

@Composable
private fun RatingDetail(label: String, rating: Float, modifier: Modifier = Modifier) {
    Column(
        modifier.clip(RoundedCornerShape(8.dp)).background(VoxelSurfaceVariant).padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, color = VoxelTextSecondary, fontSize = 11.sp)
        Text("${formatRating(rating)}/5", color = White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

private fun formatRating(rating: Float): String = String.format(Locale.getDefault(), "%.1f", rating)
