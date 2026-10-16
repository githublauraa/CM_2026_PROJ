package com.example.voxel_review.ui.screens.writeReview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

                BotonPublicarReview(
                    onClick = {
                        if (reviewIdToEdit != null) {
                            writeReviewViewModel.updateReview(
                                reviewId = reviewIdToEdit,
                                userId = currentUserId.orEmpty(),
                                videoGameId = gameId
                            )
                        } else {
                            writeReviewViewModel.publishReview(
                                videoGameId = gameId
                            )
                        }
                    },
                    text = if (isEditing) {
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
                state.errorMessage ?: profileState.errorMessage

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