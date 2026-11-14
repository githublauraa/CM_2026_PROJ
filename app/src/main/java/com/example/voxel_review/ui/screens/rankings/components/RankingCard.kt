package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.utils.ProfileImage

internal fun medalColor(position: Int) = when (position) {
    1 -> Color(0xFFFFCF73)
    2 -> Color(0xFFBFD1FC)
    3 -> Color(0xFFF4A789)
    else -> onSurfaceVariantDark
}

@Composable
internal fun RankingCard(
    position: Int,
    user: ProfileInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val featured = position == 1
    val accent = medalColor(position)
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (featured) VoxelSurfaceVariant else VoxelSurface,
        contentColor = VoxelTextPrimary,
        border = BorderStroke(1.dp, if (featured) accent.copy(alpha = 0.42f) else VoxelBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = if (featured) 16.dp else 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = 34.dp)
                    .background(accent.copy(alpha = 0.12f), CircleShape)
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = position.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = accent
                )
            }
            ProfileImage(profileImage = user.pfp, size = if (featured) 48 else 38)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = user.nick,
                    style = if (featured) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (user.numResenias == 1) "1 reseña" else "${user.numResenias} reseñas",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (featured) accent else onSurfaceVariantDark
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = onSurfaceVariantDark,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
