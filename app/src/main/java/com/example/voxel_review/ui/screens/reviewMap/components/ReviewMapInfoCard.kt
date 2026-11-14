package com.example.voxel_review.ui.screens.reviewMap.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.example.voxel_review.R
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelAccentPurple
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import java.util.Locale

@Composable
fun ReviewMapInfoCard(
    review: ReviewInfo,
    modifier: Modifier = Modifier
) {
    val coverFallback = painterResource(R.drawable.game_banner_fallback)

    Card(
        modifier = modifier.width(236.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, VoxelBorder),
        colors = CardDefaults.cardColors(
            containerColor = VoxelSurface
        )
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(VoxelPrimary, VoxelAccentPurple, VoxelSecondary)
                    )
                )
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(review.imagenJuego.takeIf { it.isNotBlank() })
                        .allowHardware(false)
                        .build(),
                    contentDescription = "Imagen del videojuego",
                    modifier = Modifier
                        .size(width = 52.dp, height = 66.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = coverFallback,
                    error = coverFallback,
                    fallback = coverFallback
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = review.tituloJuego,
                        color = VoxelTextPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = String.format(Locale.getDefault(), "★ %.1f / 5", review.ratingGeneral),
                        modifier = Modifier
                            .background(VoxelSurfaceVariant, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        color = VoxelAccentYellow,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = "Por ${review.username.ifBlank { "Sin nombre" }}",
                        color = VoxelTextSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (review.descripcion.isNotBlank()) {
                HorizontalDivider(color = VoxelBorder)
                Text(
                    text = review.descripcion.trim(),
                    color = VoxelTextPrimary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = "Ver reseña completa",
                color = VoxelPrimary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
