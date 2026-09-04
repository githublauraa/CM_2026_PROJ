package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ReviewDetailScreen(
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
    reviewViewModel: ReviewViewModel = viewModel()
) {

    val state by reviewViewModel.uiState.collectAsState()

    ReviewDetailScreenContent(
        state = state,
        onClickReview = onClickReview,
        onBackClick = onBackClick
    )
}

@Composable
fun ReviewDetailScreenContent(
    state: ReviewDetailState,
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {

            ReviewDetailTopBar(
                onBackClick = onBackClick
            )

            ReviewDetailContent(
                state = state,
                onClickReview = onClickReview
            )
        }
    }
}