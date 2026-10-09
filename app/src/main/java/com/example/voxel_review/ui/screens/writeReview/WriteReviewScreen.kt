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
    gameId: String,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(gameId) {
        writeReviewViewModel.loadGame(gameId = gameId)
    }

    WriteReviewScreen(
        writeReviewViewModel = writeReviewViewModel,
        onBackClick = onBackClick,
        onSettingsClick = onSettingsClick,
        gameId = gameId,
        modifier = modifier
    )
}

@Composable
fun WriteReviewScreen(
    writeReviewViewModel: WriteReviewViewModel,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    gameId: String,
    modifier: Modifier = Modifier
) {

    val state by writeReviewViewModel.uiState.collectAsState()
    val game = state.game

    LaunchedEffect(state.isPublished) {
        if (state.isPublished){
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
                    writeReviewViewModel.publishReview(
                        userId = "2",//modificar de acuerdo al usuario logueado
                        videoGameId = gameId
                    )
                }
            )
            state.errorMessage?.let {
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
        onBackClick = {},
        onSettingsClick = {},
        gameId = "1"
    )
}