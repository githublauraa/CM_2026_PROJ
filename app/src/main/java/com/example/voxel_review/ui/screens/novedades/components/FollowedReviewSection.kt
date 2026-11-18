package com.example.voxel_review.ui.screens.novedades.components

import com.example.voxel_review.ui.utils.reviewTitle
import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.theme.*
import com.example.voxel_review.ui.utils.GameImage
import com.example.voxel_review.ui.utils.ProfileImage
import java.util.Locale
import com.example.voxel_review.ui.utils.LocalBottomBarPadding

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
            text = stringResource(R.string.feed_no_recent_followed_reviews),
            color = VoxelTextSecondary,
            fontSize = 14.sp,
            modifier = modifier.padding(vertical = 20.dp)
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp + LocalBottomBarPadding.current)
        ) {
            items(reviews, key = { it.idResenia }) { review ->
                FollowedReviewCard(review, { onReviewClick(review) }, { onUserProfileClick(review.userId) })
            }
        }
    }
}

@Composable
private fun FollowedReviewCard(
    review: ReviewInfo,
    onClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = VoxelSurface,
        border = BorderStroke(1.dp, VoxelBorder)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                GameImage(
                    imageUrl = review.imagenJuego,
                    contentDescription = stringResource(R.string.game_cover, review.tituloJuego),
                    modifier = Modifier.size(width = 74.dp, height = 104.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        review.tituloJuego.ifBlank { stringResource(R.string.game_unavailable) },
                        style = MaterialTheme.typography.titleMedium,
                        color = VoxelTextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                    if (review.desarrollador.isNotBlank()) {
                        Text(
                            review.desarrollador,
                            style = MaterialTheme.typography.labelMedium,
                            color = onSurfaceVariantDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    RatingBadge(
                        rating = "${formatRating(review.ratingGeneral)}/5",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            HorizontalDivider(color = VoxelBorder)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(VoxelSurfaceVariant.copy(alpha = 0.45f))
                    .clickable(onClick = onProfileClick)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(profileImage = review.imagenUsuario, size = 32)
                Text(
                    review.username.ifBlank { stringResource(R.string.view_profile) },
                    color = VoxelTextPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (review.tituloDescripcion.isNotBlank()) {
                Text(
                    reviewTitle(review),
                    style = MaterialTheme.typography.titleMedium,
                    color = VoxelTextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                review.descripcion, color = VoxelTextSecondary, fontSize = 14.sp,
                lineHeight = 21.sp, maxLines = 3, overflow = TextOverflow.Ellipsis
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.likes_count, review.cantidadLikes), color = VoxelTextSecondary, fontSize = 12.sp)
                TextButton(onClick = onClick) { Text(stringResource(R.string.view_review), color = VoxelPrimary) }
            }
        }
    }
}

private fun formatRating(rating: Float): String = String.format(Locale.getDefault(), "%.1f", rating)
