package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    reviewViewModel: ReviewViewModel,
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by reviewViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            ReviewDetailTopBar(
                onBackClick = onBackClick
            )
            ReviewDetailContent(
                onClickReview = onClickReview
            )
        }
    }
}

@Composable
@Preview
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen(
        reviewViewModel = viewModel(),
        onClickReview = {},
        onBackClick = {}
    )

}
