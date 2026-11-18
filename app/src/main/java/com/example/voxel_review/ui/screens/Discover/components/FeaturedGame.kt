package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.ui.res.pluralStringResource
import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.utils.GameImage

@Composable
internal fun FeaturedGame(
    game: TrendingSearchInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(18.dp)
    val details = listOfNotNull(
        game.categoria.takeIf { it.isNotBlank() },
        when (game.reviewCount) {
            0 -> null
            1 -> stringResource(R.string.one_review)
            else -> pluralStringResource(R.plurals.review_count, game.reviewCount, game.reviewCount)
        }
    ).joinToString(" · ")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(VoxelSurface)
            .clickable(onClickLabel = stringResource(R.string.open_game), onClick = onClick)
    ) {
        GameImage(
            game.imagen,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().aspectRatio(2f)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if (game.reviewCount > 0) stringResource(R.string.most_reviewed) else stringResource(R.string.featured_game),
                    style = MaterialTheme.typography.labelMedium,
                    color = VoxelPrimary
                )
                Text(
                    text = game.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = VoxelTextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (details.isNotEmpty()) {
                    Text(
                        text = details,
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantDark
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = VoxelPrimary
            )
        }
    }
}
