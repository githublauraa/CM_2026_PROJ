package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
internal fun TarjetaJuego(juego: JuegoInfo, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = VoxelSurface,
        border = BorderStroke(1.dp, VoxelBorder.copy(alpha = 0.65f))
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GameImage(
                imageUrl = juego.imagen,
                contentDescription = null,
                modifier = Modifier.size(width = 78.dp, height = 94.dp).clip(RoundedCornerShape(10.dp))
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    juego.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = VoxelTextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        juego.autor,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantDark,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "★ %.1f".format(juego.calificacion),
                        modifier = Modifier
                            .background(VoxelSurfaceVariant, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp),
                        color = VoxelAccentYellow,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (juego.descripcion.isNotBlank()) {
                    Text(
                        juego.descripcion,
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
