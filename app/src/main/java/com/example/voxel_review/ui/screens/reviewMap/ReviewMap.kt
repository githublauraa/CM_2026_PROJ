package com.example.voxel_review.ui.screens.reviewMap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.MapType
import androidx.compose.runtime.mutableStateOf
import com.google.maps.android.compose.MapProperties
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import com.google.maps.android.compose.MapUiSettings
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.maps.android.compose.GoogleMap
import androidx.compose.material3.Switch
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.novedades.components.NovedadesHeader
import com.example.voxel_review.ui.utils.FondoPantalla
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
@Composable
fun ReviewMapScreen(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        FondoPantalla(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize()) {
            NovedadesHeader(
                onBackClick = onBackClick,
                onNotificationClick = onNotificationClick,
                modifier = Modifier.padding(start = 22.dp, top = 8.dp, end = 22.dp)
            )
            ReviewMap(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ReviewMap(
    modifier: Modifier = Modifier
){
    val bogota = LatLng(4.60971,-74.08175)
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bogota, 10.9f)
    }

    Box(modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        )
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }

}
