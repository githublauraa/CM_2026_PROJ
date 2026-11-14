package com.example.voxel_review.ui.screens.rankings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.screens.rankings.components.HeaderRankingSceen
import com.example.voxel_review.ui.screens.rankings.components.LazyColumnRanking
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

/**
 * Contenido visual de la pantalla de rankings.
 * Recibe el estado y los eventos necesarios sin acceder directamente al ViewModel.
 *
 * @param state Estado actual de la pantalla de rankings.
 * @param onChangeSeleccionado Acción ejecutada al cambiar el tipo de ranking seleccionado.
 * @param onUserClick Acción ejecutada al presionar la tarjeta de un usuario.
 * @param modifier Modificador para personalizar el contenido.
 */
@Composable
fun RankingContent(
    state: RankingsState,
    onChangeSeleccionado: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUserClick: (ProfileInfo) -> Unit,
    modifier: Modifier = Modifier.testTag("homeScreen")
) {
    Column(
        modifier = modifier.fillMaxSize().background(
            Brush.verticalGradient(0f to VoxelSurface, 0.4f to VoxelBackground)
        )
    ) {
        HeaderRankingSceen(
            seleccionado = state.seleccionado,
            onChangeSeleccionado = onChangeSeleccionado
        )

        if (state.seleccionado) {
            DiscoverSearchBar(
                query = state.searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Buscar críticos...",
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            )
        }

        LazyColumnRanking(
            state = state,
            onUserClick = onUserClick,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Pantalla de rankings conectada al ViewModel.
 * Observa el estado y conecta los eventos de la interfaz con la lógica correspondiente.
 *
 * @param rankingsViewModel ViewModel encargado del estado y la lógica de rankings.
 * @param onUserClick Acción ejecutada al presionar la tarjeta de un usuario.
 * @param modifier Modificador para personalizar la pantalla.
 */
@Composable
fun RankingsScreen(
    rankingsViewModel: RankingsViewModel = viewModel(),
    onUserClick: (ProfileInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    // Convierte el StateFlow del ViewModel en un estado observable por Compose.
    val state by rankingsViewModel.uiState.collectAsState()

    RankingContent(
        state = state,
        onChangeSeleccionado = rankingsViewModel::onChangeSeleccionado,
        onUserClick = onUserClick,
        onSearchQueryChange = rankingsViewModel::onSearchQueryChange,
        modifier = modifier.testTag("homeScreen")
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun RankingScreenPreview() {
    val users = listOf("PixelKing", "VoxelMaster", "GameHunter", "RetroGamer", "DarkPlayer")
        .mapIndexed { index, name ->
            ProfileInfo("", name, name, 150 - index * 23, 0f, 0, "")
        }
    Voxel_ReviewTheme(darkTheme = true, dynamicColor = false) {
        RankingContent(
            state = RankingsState(users = users),
            onChangeSeleccionado = {},
            onSearchQueryChange = {},
            onUserClick = {}
        )
    }
}
