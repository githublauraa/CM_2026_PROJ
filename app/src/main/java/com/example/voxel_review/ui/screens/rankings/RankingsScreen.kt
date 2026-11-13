package com.example.voxel_review.ui.screens.rankings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.voxel_review.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.appendPlaceholders
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.screens.rankings.components.HeaderRankingSceen
import com.example.voxel_review.ui.screens.rankings.components.LazyColumnRanking
import com.example.voxel_review.ui.utils.FondoPantalla

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
        modifier = modifier.fillMaxSize()
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
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
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

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla()

        RankingContent(
            state = state,
            onChangeSeleccionado = rankingsViewModel::onChangeSeleccionado,
            onUserClick = onUserClick,
            onSearchQueryChange = rankingsViewModel::onSearchQueryChange
        )
    }
}

/**
 * Vista previa de la pantalla de rankings.
 */
@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {
    RankingsScreen(
        onUserClick = {}
    )
}