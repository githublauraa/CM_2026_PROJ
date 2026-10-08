package com.example.voxel_review.ui.screens.review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    ReviewDetailScreenContent(
        state = state,
        onClickReview = onClickReview,
        onBackClick = onBackClick
    )
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

