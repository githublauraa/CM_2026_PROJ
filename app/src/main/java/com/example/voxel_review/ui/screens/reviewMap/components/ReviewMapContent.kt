package com.example.voxel_review.ui.screens.reviewMap.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.example.voxel_review.data.info.review.ReviewInfo
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ReviewMapContent(
    state: ReviewMapState,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var hasLocationPermission by remember(context) {
        mutableStateOf(hasMapLocationPermission(context))
    }
    LifecycleResumeEffect(context) {
        hasLocationPermission = hasMapLocationPermission(context)
        onPauseOrDispose { }
    }

    val bogota = LatLng(4.60971, -74.08175)
    var showZoomControls by remember { mutableStateOf(MapUiSettings().zoomControlsEnabled) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bogota, 10.9f)
    }

    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }
    val locationClient = remember(context) { LocationServices.getFusedLocationProviderClient(context) }
    var locating by remember { mutableStateOf(false) }

    fun centerOnLocation() {
        if (locating) return
        locating = true
        scope.launch {
            val cancellation = CancellationTokenSource()
            val error = try {
                val manager = context.getSystemService(LocationManager::class.java)
                if (manager == null || !LocationManagerCompat.isLocationEnabled(manager)) {
                    "Activa la ubicación del dispositivo para centrar el mapa."
                } else {
                    val precise = ContextCompat.checkSelfPermission(
                        context, Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                    val request = CurrentLocationRequest.Builder()
                        .setPriority(
                            if (precise) Priority.PRIORITY_HIGH_ACCURACY
                            else Priority.PRIORITY_BALANCED_POWER_ACCURACY
                        )
                        .setMaxUpdateAgeMillis(0L)
                        .setDurationMillis(8_000L)
                        .build()
                    val location = locationClient.getCurrentLocation(request, cancellation.token).await()
                    if (location == null) {
                        "No se pudo obtener tu ubicación. Inténtalo de nuevo."
                    } else {
                        val zoom = if (precise) 16f else 13f
                        val point = LatLng(location.latitude, location.longitude)
                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(point, zoom))
                        null
                    }
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                "No se pudo obtener tu ubicación. Inténtalo de nuevo."
            } finally {
                cancellation.cancel()
                locating = false
            }
            if (error != null) snackbar.showSnackbar(error)
        }
    }

    val requestLocation = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        hasLocationPermission = hasMapLocationPermission(context)
        if (hasLocationPermission) {
            centerOnLocation()
        } else {
            scope.launch { snackbar.showSnackbar("Permite el acceso a la ubicación en los ajustes de la app.") }
        }
    }

    val groups = remember(state.reviews) { reviewsByLocation(state.reviews) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

    Box(modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = showZoomControls,
                myLocationButtonEnabled = false
            ),
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
            checked = showZoomControls,
            onCheckedChange = { showZoomControls = it }
        )
        FilledIconButton(
            onClick = {
                if (hasMapLocationPermission(context)) {
                    centerOnLocation()
                } else {
                    requestLocation.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    )
                }
            },
            enabled = !locating,
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
        ) {
            if (locating) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Icon(Icons.Filled.MyLocation, contentDescription = "Mi ubicación")
            }
        }
        SnackbarHost(hostState = snackbar, modifier = Modifier.align(Alignment.BottomCenter))

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


private fun hasMapLocationPermission(context: Context): Boolean =
    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED
