package com.example.voxel_review.ui.screens.reviewMap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.novedades.components.NovedadesHeader
import com.example.voxel_review.ui.screens.reviewMap.components.ReviewMapContent
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ReviewMapScreen(
    reviewMapViewModel: ReviewMapViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by reviewMapViewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier.fillMaxSize()) {
        FondoPantalla(Modifier.fillMaxSize())
        ReviewMapBody(
            state = state,
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick
        )
    }
}

@Composable
fun ReviewMapBody(
    state: ReviewMapState,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        NovedadesHeader(
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick,
            modifier = Modifier.padding(start = 22.dp, top = 8.dp, end = 22.dp)
        )
        ReviewMapContent(state = state, modifier = Modifier.weight(1f))
    }
}
