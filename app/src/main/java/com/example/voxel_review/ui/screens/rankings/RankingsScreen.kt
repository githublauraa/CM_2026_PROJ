package com.example.voxel_review.ui.screens.rankings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.rankings.components.HeaderRankingSceen
import com.example.voxel_review.ui.screens.rankings.components.LazyColumnRanking
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun RankingContent(
    state: RankingsState,
    onChangeSeleccionado: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        HeaderRankingSceen(
            seleccionado = state.seleccionado,
            onChangeSeleccionado = onChangeSeleccionado
        )

        Text(
            text = if (state.seleccionado) {
                "TOP CRÍTICOS"
            } else {
                "TOP JUEGOS"
            },
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 140.dp,
                    start = 18.dp
                )
        )

        LazyColumnRanking(
            state = state
        )
    }
}

@Composable
fun RankingsScreen(
    rankingsViewModel: RankingsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by rankingsViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla()

        RankingContent(
            state = state,
            onChangeSeleccionado = rankingsViewModel::onChangeSeleccionado
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {
    RankingsScreen()
}