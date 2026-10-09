package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Pantalla de detalle de una reseña.
 *
 * Si llega [reviewId] y [userId] (flujo del perfil) se carga esa reseña concreta;
 * en caso contrario se cargan las reseñas del videojuego [juegoId] (flujo de novedades).
 */
@Composable
fun ReviewDetailScreen(
    reviewViewModel: ReviewViewModel,
    juegoId: String,
    reviewId: String = "",
    userId: String = "",
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
) {

    val state by reviewViewModel.uiState.collectAsState()

    LaunchedEffect(juegoId, reviewId, userId) {
        if (reviewId.isNotBlank() && userId.isNotBlank()) {
            reviewViewModel.getUserReview(userId, reviewId)
        } else {
            reviewViewModel.getGameReviews(juegoId)
        }
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.errorMessage ?: "Error desconocido")
            }
        }

        state.reviews.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No hay reseñas disponibles")
            }
        }

        else -> {
            ReviewDetailScreenContent(
                state = state,
                onClickReview = onClickReview,
                onBackClick = onBackClick
            )
        }
    }
}

@Preview
@Composable
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen(
        reviewViewModel = viewModel(),
        juegoId = "1",
        reviewId = "",
        userId = "",
        onClickReview = {},
        onBackClick = {}
    )
}
