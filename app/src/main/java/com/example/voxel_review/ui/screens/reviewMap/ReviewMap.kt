package com.example.voxel_review.ui.screens.reviewMap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.novedades.components.NovedadesHeader
import com.example.voxel_review.ui.screens.reviewMap.components.ReviewMapContent
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSurface

@Composable
fun ReviewMapScreen(
    reviewMapViewModel: ReviewMapViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by reviewMapViewModel.uiState.collectAsStateWithLifecycle()

    ReviewMapBody(
        state = state,
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        onReviewClick = onReviewClick,
        modifier = modifier
    )
}

@Composable
fun ReviewMapBody(
    state: ReviewMapState,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize().background(
            Brush.verticalGradient(0f to VoxelSurface, 0.4f to VoxelBackground)
        )
    ) {
        NovedadesHeader(
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick
        )
        ReviewMapContent(
            state = state,
            onReviewClick = onReviewClick,
            modifier = Modifier.weight(1f)
        )
    }
}
