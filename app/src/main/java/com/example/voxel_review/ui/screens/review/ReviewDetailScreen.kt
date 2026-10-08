package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun ReviewDetailScreen(
    reviewViewModel: ReviewViewModel,
    juegoId: String,
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
) {

    val state by reviewViewModel.uiState.collectAsState()

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text =state.errorMessage ?: "Error desconocido")
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
        onClickReview = {},
        onBackClick = {}
    )
}

