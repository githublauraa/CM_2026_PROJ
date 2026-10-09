package com.example.voxel_review.ui.screens.writeReview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.ui.screens.profile.ProfileViewModel
import com.example.voxel_review.ui.theme.onErrorLight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import com.example.voxel_review.ui.screens.writeReview.components.BotonPublicarReview
import com.example.voxel_review.ui.screens.writeReview.components.GameCalification
import com.example.voxel_review.ui.screens.writeReview.components.GameInfo
import com.example.voxel_review.ui.screens.writeReview.components.ReviewText
import com.example.voxel_review.ui.screens.writeReview.components.TopBar
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

    LaunchedEffect(gameId) {
        writeReviewViewModel.loadGame(gameId = gameId)
    }

    // En modo edición se precarga la reseña a editar (texto y calificaciones)
    LaunchedEffect(reviewId, userId) {
        if (reviewId.isNotBlank() && userId.isNotBlank()) {
            writeReviewViewModel.loadReviewForEdit(userId = userId, reviewId = reviewId)
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

    val isEditing = reviewId.isNotBlank()

    LaunchedEffect(state.isPublished) {
        if (state.isPublished){
            onBackClick()
        }
    }

    // Al actualizar la reseña se vuelve a la pantalla anterior
    LaunchedEffect(profileState.isReviewUpdated) {
        if (profileState.isReviewUpdated) {
            onBackClick()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.TopCenter)
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
                    anio = game.lanzamiento,
                )
            }

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
                onTextChange = { writeReviewViewModel.updateReviewText(it) }
            )

            BotonPublicarReview(
                onClick = {
                    if (isEditing) {
                        profileViewModel.updateReview(
                            reviewId = reviewId,
                            review = CreateReviewDto(
                                userId = userId.toIntOrNull() ?: 0,
                                videoGameId = gameId.toIntOrNull() ?: 0,
                                gameplayRating = state.gameplayRating,
                                graphicsRating = state.graphicsRating,
                                storyRating = state.storyRating,
                                content = state.reviewText
                            )
                        )
                    } else {
                        writeReviewViewModel.publishReview(
                            userId = "2",//modificar de acuerdo al usuario logueado
                            videoGameId = gameId
                        )
                    }
                },
                text = if (isEditing) "Actualizar reseña" else "PUBLICAR REVIEW"
            )

            val errorMessage = profileState.errorMessage ?: state.errorMessage
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
