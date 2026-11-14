package com.example.voxel_review.ui.screens.reviewMap.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.voxel_review.ui.screens.reviewMap.ReviewMapState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.voxel_review.data.info.review.ReviewInfo
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ReviewMapContent(
    state: ReviewMapState,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val bogota = LatLng(4.60971, -74.08175)
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bogota, 10.9f)
    }

    val groups = remember(state.reviews) { reviewsByLocation(state.reviews) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

    Box(modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState,
            contentDescription = "Mapa reseñas voxelReview"
        ) {
            ReviewMapMarkers(
                groups = groups,
                onReviewClick = onReviewClick,
                onClusterClick = { selectedLocation = it }
            )
        }
        when {
            state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            state.error != null -> Text(state.error, Modifier.align(Alignment.TopCenter))
            state.reviews.isEmpty() -> Text(
                "No hay reseñas con ubicación de las últimas 24 horas",
                Modifier.align(Alignment.TopCenter)
            )
        }
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )

    }

    selectedLocation?.let { location ->
        val reviews = groups[location].orEmpty()
        if (reviews.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = { selectedLocation = null },
                title = { Text("${reviews.size} reseñas en este punto") },
                text = {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 360.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(reviews, key = { it.idResenia }) { review ->
                            ReviewMapInfoCard(
                                review = review,
                                modifier = Modifier.fillMaxWidth().clickable {
                                    selectedLocation = null
                                    onReviewClick(review)
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedLocation = null }) { Text("Cerrar") }
                }
            )
        }
    }
}
