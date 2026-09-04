package com.example.voxel_review.ui.screens.Discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.Discover.components.DiscoverTopBar
import com.example.voxel_review.ui.screens.Discover.components.TrendingSearchesSection
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.screens.Discover.components.GenreFilterSection

/**
 * Contenido visual principal de la pantalla de descubrimiento.
 * Renderiza la barra superior, la barra de búsqueda, los filtros por género y las tendencias.
 *
 * @param discoverViewModel ViewModel que administra el estado e interacciones de la pantalla.
 * @param onBackClick Acción ejecutada al presionar el botón de regresar.
 * @param onNotificationClick Acción ejecutada al presionar el icono de notificaciones.
 * @param onItemClick Acción ejecutada al presionar un elemento en tendencia.
 * @param modifier Modificador para personalizar el contenedor principal.
 */
@Composable
fun DiscoverScreen(
    discoverViewModel: DiscoverViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onItemClick: (TrendingSearchInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    val state by discoverViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.voxel_background)
                )
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            DiscoverTopBar(
                onBackClick = onBackClick,
                onNotificationClick = onNotificationClick
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DiscoverSearchBar(
                query = state.searchQuery,
                onQueryChange = {
                    discoverViewModel.updateSearchQuery(it)
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            GenreFilterSection(
                genres = state.genres,
                selectedGenre = state.selectedGenre,
                onGenreSelected = {
                    discoverViewModel.updateSelectedGenre(it)
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            TrendingSearchesSection(
                items = state.trendingSearches,
                onItemClick = onItemClick
            )

            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

@Preview
@Composable
fun DiscoverScreenPreview() {
    DiscoverScreen(
        discoverViewModel = viewModel(),
        onBackClick = {},
        onNotificationClick = {},
        onItemClick = {}
    )
}
