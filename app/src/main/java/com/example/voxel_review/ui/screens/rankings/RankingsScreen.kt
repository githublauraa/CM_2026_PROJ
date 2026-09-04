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

/**
 * Contenido visual de la pantalla de rankings.
 * Recibe el estado y los eventos necesarios sin acceder directamente al ViewModel.
 *
 * @param state Estado actual de la pantalla de rankings.
 * @param onChangeSeleccionado Acción ejecutada al cambiar el tipo de ranking seleccionado.
 * @param modifier Modificador para personalizar el contenido.
 */
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

        // El título cambia de acuerdo con el tipo de ranking seleccionado.
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

/**
 * Pantalla de rankings conectada al ViewModel.
 * Observa el estado y conecta los eventos de la interfaz con la lógica correspondiente.
 *
 * @param rankingsViewModel ViewModel encargado del estado y la lógica de rankings.
 * @param modifier Modificador para personalizar la pantalla.
 */
@Composable
fun RankingsScreen(
    rankingsViewModel: RankingsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Convierte el StateFlow del ViewModel en un estado observable por Compose.
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

/**
 * Vista previa de la pantalla de rankings.
 */
@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {
    RankingsScreen()
}