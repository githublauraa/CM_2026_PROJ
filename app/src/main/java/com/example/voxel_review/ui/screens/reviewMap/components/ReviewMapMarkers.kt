package com.example.voxel_review.ui.screens.reviewMap.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindowComposable
import com.google.maps.android.compose.rememberUpdatedMarkerState
import java.util.Locale

@Composable
@GoogleMapComposable
fun ReviewMapMarkers(
    groups: Map<LatLng, List<ReviewInfo>>,
    onReviewClick: (ReviewInfo) -> Unit,
    onClusterClick: (LatLng) -> Unit
) {
    groups.forEach { (position, group) ->
        key(position) {
            if (group.size == 1) {
                val review = group.single()
                val rating = String.format(Locale.getDefault(), "%.1f", review.ratingGeneral)
                MarkerInfoWindowComposable(
                    rating,
                    state = rememberUpdatedMarkerState(position = position),
                    title = review.tituloJuego,
                    onInfoWindowClick = { onReviewClick(review) },
                    infoContent = { ReviewMapInfoCard(review = review) }
                ) {
                    ReviewRatingPin(rating)
                }
            } else {
                val title = "${group.size} reseñas"
                MarkerComposable(
                    group.size,
                    state = rememberUpdatedMarkerState(position = position),
                    title = title,
                    contentDescription = title,
                    anchor = Offset(0.5f, 0.5f),
                    zIndex = 1f,
                    onClick = {
                        onClusterClick(position)
                        true
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(VoxelSurface, CircleShape)
                            .border(2.dp, VoxelPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = group.size.toString(),
                            color = VoxelPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewRatingPin(rating: String) {
    val shape = RoundedCornerShape(12.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(VoxelSurface, shape)
                .border(2.dp, VoxelSecondary, shape)
                .padding(horizontal = 9.dp, vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "★ $rating",
                color = VoxelAccentYellow,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Canvas(Modifier.width(12.dp).height(7.dp)) {
            val point = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width / 2, size.height)
                close()
            }
            drawPath(point, VoxelSecondary)
        }
    }
}

internal fun reviewsByLocation(reviews: List<ReviewInfo>): Map<LatLng, List<ReviewInfo>> {
    return reviews.mapNotNull { review ->
        val latitude = review.latitude ?: return@mapNotNull null
        val longitude = review.longitude ?: return@mapNotNull null
        LatLng(latitude, longitude) to review
    }.groupBy({ it.first }, { it.second })
}
