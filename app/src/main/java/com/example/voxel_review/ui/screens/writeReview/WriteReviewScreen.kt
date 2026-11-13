package com.example.voxel_review.ui.screens.writeReview

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.CancellationSignal
import android.os.SystemClock
import androidx.core.location.LocationManagerCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.profile.ProfileViewModel
import com.example.voxel_review.ui.screens.writeReview.components.BotonPublicarReview
import com.example.voxel_review.ui.screens.writeReview.components.GameCalification
import com.example.voxel_review.ui.screens.writeReview.components.GameInfo
import com.example.voxel_review.ui.screens.writeReview.components.ReviewText
import com.example.voxel_review.ui.screens.writeReview.components.TopBar
import com.example.voxel_review.ui.theme.onErrorLight
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun WriteReviewRoute(
    writeReviewViewModel: WriteReviewViewModel,
    profileViewModel: ProfileViewModel,
    gameId: String,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    reviewId: String = "",
    userId: String = "",
    modifier: Modifier = Modifier
) {
    val currentUserId = writeReviewViewModel.currentUserId()

    val canWriteReview =
        !currentUserId.isNullOrBlank() &&
                (userId.isBlank() || userId == currentUserId)

    LaunchedEffect(gameId, reviewId, userId, currentUserId) {
        writeReviewViewModel.loadGame(gameId)

        if (canWriteReview) {
            if (reviewId.isNotBlank()) {
                writeReviewViewModel.loadReviewForEdit(
                    userId = currentUserId.orEmpty(),
                    reviewId = reviewId
                )
            } else {
                writeReviewViewModel.loadExistingReview(gameId)
            }
        }
    }

    WriteReviewScreen(
        writeReviewViewModel = writeReviewViewModel,
        profileViewModel = profileViewModel,
        onBackClick = onBackClick,
        onSettingsClick = onSettingsClick,
        gameId = gameId,
        reviewId = reviewId,
        userId = userId,
        modifier = modifier
    )
}

@Composable
fun WriteReviewScreen(
    writeReviewViewModel: WriteReviewViewModel,
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    gameId: String,
    reviewId: String = "",
    userId: String = "",
    modifier: Modifier = Modifier
) {
    val state by writeReviewViewModel.uiState.collectAsState()
    val profileState by profileViewModel.uiState.collectAsState()

    val game = state.game

    val reviewIdToEdit = state.editingReviewId
        ?: reviewId.takeIf { it.isNotBlank() }

    val isEditing = reviewIdToEdit != null
    val currentUserId = writeReviewViewModel.currentUserId()

    val canWriteReview =
        !currentUserId.isNullOrBlank() &&
                (userId.isBlank() || userId == currentUserId)

    // Para una edición explícita, espera a que se cargue esa reseña.
    val isReviewReady =
        reviewId.isBlank() || state.editingReviewId == reviewId

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var shareLocation by rememberSaveable { mutableStateOf(false) }
    var waitingForLocation by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }
    val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION

    fun publishWithLocation() {
        scope.launch {
            val requestedAt = SystemClock.elapsedRealtimeNanos()
            val cancellation = CancellationTokenSource()
            try {
                val publicationLocation = try {
                    withTimeoutOrNull(7_000L) {
                        val manager = context.getSystemService(LocationManager::class.java)
                        if (manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
                            val gpsLocation = withTimeoutOrNull(3_500L) {
                                suspendCancellableCoroutine { continuation ->
                                    val signal = CancellationSignal()
                                    continuation.invokeOnCancellation { signal.cancel() }
                                    LocationManagerCompat.getCurrentLocation(
                                        manager,
                                        LocationManager.GPS_PROVIDER,
                                        signal,
                                        ContextCompat.getMainExecutor(context)
                                    ) { fix -> continuation.resume(fix) }
                                }
                            }
                            if (gpsLocation != null &&
                                gpsLocation.elapsedRealtimeNanos >= requestedAt
                            ) {
                                return@withTimeoutOrNull gpsLocation
                            }
                        }

                        val request = CurrentLocationRequest.Builder()
                            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                            .setGranularity(Granularity.GRANULARITY_COARSE)
                            .setMaxUpdateAgeMillis(0L)
                            .setDurationMillis(3_000L)
                            .build()
                        locationClient.getCurrentLocation(request, cancellation.token).await()
                            ?.takeIf { it.elapsedRealtimeNanos >= requestedAt }
                    }
                } catch (exception: CancellationException) {
                    throw exception
                } catch (_: Exception) {
                    null
                }

                if (publicationLocation == null) {
                    locationError = "No se pudo obtener tu ubicación. Actívala e inténtalo de nuevo, o desmarca la opción para publicar sin aparecer en el mapa."
                } else {
                    writeReviewViewModel.publishReview(
                        videoGameId = gameId,
                        latitude = publicationLocation.latitude,
                        longitude = publicationLocation.longitude
                    )
                }
            } finally {
                cancellation.cancel()
                waitingForLocation = false
            }
        }
    }

    val requestLocation = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            waitingForLocation = true
            publishWithLocation()
        } else {
            waitingForLocation = false
            locationError = "Para aparecer en el mapa, permite el acceso a la ubicación. Puedes desmarcar la opción para publicar sin ubicación."
        }
    }

    LaunchedEffect(state.isPublished) {
        if (state.isPublished) {
            onBackClick()
        }
    }

    LaunchedEffect(profileState.isReviewUpdated) {
        if (profileState.isReviewUpdated) {
            onBackClick()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState())
        ) {
            TopBar(
                onBackClick = onBackClick,
                onSettingsClick = onSettingsClick
            )

            if (game != null) {
                GameInfo(
                    imagen = game.imagen,
                    nombre = game.nombre,
                    desarrollador = game.desarrollador,
                    anio = game.lanzamiento
                )
            }

            if (canWriteReview && isReviewReady) {
                GameCalification(
                    gameplayRating = state.gameplayRating,
                    graphicsRating = state.graphicsRating,
                    storyRating = state.storyRating,
                    onGameplayRatingChange = {
                        writeReviewViewModel.updateGameplayRating(it)
                    },
                    onGraphicsRatingChange = {
                        writeReviewViewModel.updateGraphicsRating(it)
                    },
                    onStoryRatingChange = {
                        writeReviewViewModel.updateStoryRating(it)
                    }
                )

                ReviewText(
                    text = state.reviewText,
                    onTextChange = {
                        writeReviewViewModel.updateReviewText(it)
                    }
                )

                if (!isEditing) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = shareLocation,
                            onCheckedChange = {
                                shareLocation = it
                                locationError = null
                            },
                            enabled = !waitingForLocation
                        )
                        Text("Mostrar esta reseña en el mapa con mi ubicación aproximada")
                    }
                }

                BotonPublicarReview(
                    enabled = !waitingForLocation,
                    onClick = {
                        locationError = null
                        if (reviewIdToEdit != null) {
                            writeReviewViewModel.updateReview(
                                reviewId = reviewIdToEdit,
                                userId = currentUserId.orEmpty(),
                                videoGameId = gameId
                            )
                        } else if (!shareLocation || state.reviewText.isBlank()) {
                            writeReviewViewModel.publishReview(videoGameId = gameId)
                        } else {
                            waitingForLocation = true
                            if (ContextCompat.checkSelfPermission(context, locationPermission) ==
                                PackageManager.PERMISSION_GRANTED
                            ) {
                                publishWithLocation()
                            } else {
                                requestLocation.launch(locationPermission)
                            }
                        }
                    },
                    text = if (waitingForLocation) {
                        "Obteniendo ubicación…"
                    } else if (isEditing) {
                        "Actualizar reseña"
                    } else {
                        "PUBLICAR REVIEW"
                    }
                )
            } else if (!canWriteReview) {
                Text(
                    text = if (currentUserId.isNullOrBlank()) {
                        "Debes iniciar sesión para escribir una reseña."
                    } else {
                        "No puedes escribir o editar reseñas de otro usuario."
                    },
                    color = onErrorLight
                )
            }

            val errorMessage =
                locationError ?: state.errorMessage ?: profileState.errorMessage

            errorMessage?.let {
                Text(
                    text = it,
                    color = onErrorLight
                )
            }
        }
    }
}

@Composable
@Preview
fun WriteReviewScreenPreview() {
    WriteReviewScreen(
        writeReviewViewModel = viewModel(),
        profileViewModel = viewModel(),
        onBackClick = {},
        onSettingsClick = {},
        gameId = "1"
    )
}
