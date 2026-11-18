package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.utils.GameImage

@Composable
internal fun TarjetaJuego(
    juego: JuegoInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = VoxelSurface,
        border = BorderStroke(1.dp, VoxelBorder)
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 120.dp)
                .height(IntrinsicSize.Min)
                .background(
                    Brush.horizontalGradient(listOf(VoxelSurfaceVariant, VoxelSurface))
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GameImage(
                imageUrl = juego.imagen,
                contentDescription = null,
                modifier = Modifier.width(92.dp).fillMaxHeight()
            )
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = juego.nombre,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = VoxelTextPrimary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = onSurfaceVariantDark,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = juego.autor,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelMedium,
                        color = onSurfaceVariantDark,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    RatingBadge(rating = "%.1f".format(juego.calificacion))
                }
                if (juego.descripcion.isNotBlank()) {
                    Text(
                        text = juego.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantDark,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
internal fun RatingBadge(rating: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(VoxelSurfaceVariant, CircleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = VoxelAccentYellow,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = rating,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = VoxelAccentYellow
        )
    }
}
