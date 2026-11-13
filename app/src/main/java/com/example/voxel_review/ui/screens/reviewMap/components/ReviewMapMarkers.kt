package com.example.voxel_review.ui.screens.reviewMap.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.example.voxel_review.data.info.review.ReviewInfo
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
@GoogleMapComposable
fun ReviewMapMarkers(
    reviews: List<ReviewInfo>,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    reviews.forEach { review ->
        val latitude = review.latitude ?: return@forEach
        val longitude = review.longitude ?: return@forEach

        key(review.idResenia) {
            MarkerInfoWindow(
                state = rememberUpdatedMarkerState(position = LatLng(latitude, longitude)),
                title = review.tituloJuego,
                onInfoWindowClick = { onReviewClick(review) }
            ) {
                ReviewMapInfoCard(
                    review = review,
                    modifier = modifier
                )
            }
        }
    }
}
