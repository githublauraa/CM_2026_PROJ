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
import com.example.voxel_review.data.review.ReviewInfo
import com.example.voxel_review.ui.screens.profile.ProfileViewModel

/**
 * Pantalla de detalle de una reseña.
 *
 * Si llega [reviewId] y [userId] (flujo del perfil) se carga esa reseña concreta;
 * en caso contrario se cargan las reseñas del videojuego [juegoId] (flujo de novedades).
 */
@Composable
fun ReviewDetailScreen(
    reviewViewModel: ReviewViewModel,
    profileViewModel: ProfileViewModel,
    juegoId: String,
    reviewId: String = "",
    userId: String = "",
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
    onEditReview: (ReviewInfo) -> Unit,
) {

    val state by reviewViewModel.uiState.collectAsState()
    val profileState by profileViewModel.uiState.collectAsState()

    LaunchedEffect(juegoId, reviewId, userId) {
        if (reviewId.isNotBlank() && userId.isNotBlank()) {
            reviewViewModel.getUserReview(userId, reviewId)
        } else {
            reviewViewModel.getGameReviews(juegoId)
        }
    }

    // Al eliminar la reseña se vuelve a la pantalla anterior
    LaunchedEffect(profileState.isReviewDeleted) {
        if (profileState.isReviewDeleted) {
            onBackClick()
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
                onBackClick = onBackClick,
                onEditReview = onEditReview,
                onDeleteReview = { review ->
                    profileViewModel.deleteReview(
                        reviewId = review.idResenia,
                        profileId = review.userId
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen(
        reviewViewModel = viewModel(),
        profileViewModel = viewModel(),
        juegoId = "1",
        reviewId = "",
        userId = "",
        onClickReview = {},
        onBackClick = {},
        onEditReview = {}
    )
}
