package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.privacysandbox.ads.adservices.adid.AdId

@Composable
fun ReviewDetailScreen(
    reviewId: String,
    reviewViewModel: ReviewViewModel,
    onCommentReplyClicked: (String) -> Unit,
    onCommentDetailClicked: (String) -> Unit,
    juegoId: String,
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
) {

    val state by reviewViewModel.uiState.collectAsState()

    LaunchedEffect(reviewId) {
        reviewViewModel.getReviewComments(reviewId)
    }

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

