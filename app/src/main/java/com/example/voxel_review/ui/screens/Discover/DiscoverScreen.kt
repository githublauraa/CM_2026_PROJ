package com.example.voxel_review.ui.screens.Discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.screens.Discover.components.DiscoverTopBar
import com.example.voxel_review.ui.screens.Discover.components.FeaturedGame
import com.example.voxel_review.ui.screens.Discover.components.TrendingSearchItem
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun DiscoverScreen(
    discoverViewModel: DiscoverViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onItemClick: (TrendingSearchInfo) -> Unit,
    modifier: Modifier = Modifier.testTag("discover")
) {
    val state by discoverViewModel.uiState.collectAsState()
    val searching = state.searchQuery.isNotBlank()
    val featured = if (searching) null else state.trendingSearches.let { games ->
        games.maxWithOrNull(
            compareBy<TrendingSearchInfo> { it.reviewCount }.thenBy { it.imagen.isNotBlank() }
        )
    }
    val games = if (featured == null) state.filteredTrendingSearches
        else state.trendingSearches.filterNot { it.id == featured.id }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(0f to VoxelSurface, 0.4f to VoxelBackground))
            .imePadding()
    ) {
        DiscoverTopBar(onBackClick = onBackClick, onNotificationClick = onNotificationClick)
        DiscoverSearchBar(
            query = state.searchQuery,
            onQueryChange = discoverViewModel::updateSearchQuery,
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 20.dp)
        ) {
            if (featured != null) {
                item {
                    FeaturedGame(
                        game = featured,
                        onClick = { onItemClick(featured) },
                        modifier = Modifier.testTag("juego")
                    )
                }
            }
            if (searching || games.isNotEmpty()) {
                item {
                    Text(
                        text = if (searching) "Resultados" else "En tendencia",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = VoxelTextPrimary,
                        modifier = Modifier.padding(top = if (featured != null) 22.dp else 8.dp, bottom = 6.dp)
                    )
                }
            }
            items(games) { game ->
                TrendingSearchItem(
                    item = game,
                    onClick = { onItemClick(game) },
                    modifier = Modifier.testTag("juego")
                )
                HorizontalDivider(color = VoxelBorder.copy(alpha = 0.65f))
            }
            if (featured == null && games.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth().padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        when {
                            state.isLoading -> CircularProgressIndicator(color = VoxelPrimary)
                            state.error != null -> {
                                Text("No pudimos cargar los juegos", color = onSurfaceVariantDark)
                                TextButton(onClick = discoverViewModel::loadVideoGames) {
                                    Text("Reintentar", color = VoxelPrimary)
                                }
                            }
                            else -> Text(
                                text = if (searching) "No encontramos juegos con ese nombre"
                                    else "Aún no hay juegos para explorar",
                                color = onSurfaceVariantDark
                            )
                        }
                    }
                }
            }
        }
    }
}
