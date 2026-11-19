package com.example.voxel_review.ui.screens.writeReview

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.selection.toggleable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.platform.testTag
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.background
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSurface
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.TextButton
import com.example.voxel_review.ui.theme.errorDark
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
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
    val location_unavailable_reviewText = stringResource(R.string.location_unavailable_review)
    val location_permission_reviewText = stringResource(R.string.location_permission_review)
    val scope = rememberCoroutineScope()
    val reviewLocationProvider = remember(context) {
        com.example.voxel_review.data.dataSource.services.ReviewLocationProvider(context)
    }
    var shareLocation by rememberSaveable { mutableStateOf(false) }
    var waitingForLocation by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }
    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    fun publishWithLocation() {
        scope.launch {
            try {
                val publicationLocation = reviewLocationProvider.getLocation()
                if (publicationLocation == null) {
                    locationError = location_unavailable_reviewText
                } else {
                    writeReviewViewModel.publishReview(
                        videoGameId = gameId,
                        latitude = publicationLocation.latitude,
                        longitude = publicationLocation.longitude
                    )
                }
            } finally {
                waitingForLocation = false
            }
        }
    }

    val requestLocation = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.any { it }) {
            waitingForLocation = true
            publishWithLocation()
        } else {
            waitingForLocation = false
            locationError = location_permission_reviewText
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
        modifier = modifier.fillMaxSize().imePadding()
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(0.9f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 16.dp)
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

                    Column(Modifier.fillMaxWidth()) {
                        ReviewOptionRow(
                            text = stringResource(R.string.review_contains_spoilers),
                            checked = state.containsSpoilers,
                            enabled = !waitingForLocation,
                            tag = "reviewContainsSpoilers",
                            onCheckedChange = writeReviewViewModel::updateContainsSpoilers
                        )
                        if (!isEditing) {
                            ReviewOptionRow(
                                text = stringResource(R.string.review_include_location),
                                checked = shareLocation,
                                enabled = !waitingForLocation,
                                tag = "reviewIncludeLocation",
                                onCheckedChange = {
                                    shareLocation = it
                                    locationError = null
                                }
                            )
                        }
                    }
                } else if (!canWriteReview) {
                    Text(
                        text = if (currentUserId.isNullOrBlank()) {
                            stringResource(R.string.sign_in_to_review)
                        } else {
                            stringResource(R.string.review_other_user_denied)
                        },
                        color = onErrorLight
                    )
                }

            }
            if (canWriteReview && isReviewReady) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(VoxelSurface)
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    val errorMessage = locationError ?: state.errorMessage ?: profileState.errorMessage
                    errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = errorDark,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                                .testTag("reviewPublishError")
                        )
                    }
                    if (locationError != null && shareLocation && !isEditing && !waitingForLocation) {
                        TextButton(
                            onClick = {
                                shareLocation = false
                                locationError = null
                                writeReviewViewModel.publishReview(videoGameId = gameId)
                            },
                            modifier = Modifier.fillMaxWidth().testTag("publishReviewWithoutLocation")
                        ) {
                            Text(stringResource(R.string.publish_review_without_location), color = VoxelPrimary)
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
                                    requestLocation.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
                                }
                            }
                        },
                        text = if (waitingForLocation) {
                            stringResource(R.string.getting_location)
                        } else if (isEditing) {
                            stringResource(R.string.update_review)
                        } else {
                            stringResource(R.string.publish_review)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewOptionRow(
    text: String,
    checked: Boolean,
    enabled: Boolean,
    tag: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 48.dp)
            .testTag(tag)
            .toggleable(value = checked, enabled = enabled, role = Role.Checkbox, onValueChange = onCheckedChange)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            modifier = Modifier.size(24.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = VoxelPrimary,
                uncheckedColor = VoxelTextSecondary,
                checkmarkColor = VoxelBackground
            )
        )
        Text(text = text, color = VoxelTextPrimary, modifier = Modifier.weight(1f))
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
