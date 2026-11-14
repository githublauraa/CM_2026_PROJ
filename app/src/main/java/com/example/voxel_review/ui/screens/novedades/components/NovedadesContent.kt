package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.info.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.novedades.NovedadesViewModel
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.errorDark
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.utils.HeaderNotificationButton
import com.example.voxel_review.ui.utils.ScreenHeader
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R

@Composable
fun NovedadesContent(
    onMapClick : () -> Unit,
    novedadesViewModel: NovedadesViewModel,
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCategoriaSeleccionada: (String) -> Unit,
    onReviewClick: (ReviewInfo) -> Unit = {},
    onUserProfileClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {

    val state by novedadesViewModel.uiState.collectAsState()

    val following = state.selectedFilter == "Siguiendo"
    Column(
        modifier = modifier.fillMaxSize().background(
            Brush.verticalGradient(0f to VoxelSurface, 0.4f to VoxelBackground)
        )
    ) {
        ScreenHeader(
            title = stringResource(R.string.novedades_y_rese_as),
            onBack = onBackClick
        ) {
            HeaderNotificationButton(onClick = onNotificationClick)
        }
        FeedFilterSection(
            selectedFilter = state.selectedFilter,
            onFilterSelected = novedadesViewModel::selectFilter,
            onMapClick = onMapClick
        )
        Text(
            text = if (following) "Reseñas de usuarios que sigues" else "Juegos reseñados",
            style = MaterialTheme.typography.titleMedium,
            color = VoxelTextPrimary,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 8.dp)
        )
        when {
            state.isLoading -> Box(
                Modifier.fillMaxWidth().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VoxelPrimary)
            }
            state.error != null -> Text(
                text = state.error.orEmpty(),
                color = errorDark,
                modifier = Modifier.padding(20.dp)
            )
            following -> FollowedReviewsSection(
                isLoading = false,
                reviews = state.followedReviews,
                onReviewClick = onReviewClick,
                onUserProfileClick = onUserProfileClick,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            state.listaJuegos.isEmpty() -> Text(
                text = "Aún no hay juegos con reseñas",
                color = onSurfaceVariantDark,
                modifier = Modifier.padding(20.dp)
            )
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.listaJuegos, key = { it.id }) { juego ->
                    TarjetaJuego(
                        juego = juego,
                        onClick = { onClick(juego) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun NovedadesContentPreview() {
    NovedadesContent(
        onMapClick = {},
        novedadesViewModel = viewModel(),
        onCategoriaSeleccionada = {},
        onClick = {},
        onNotificationClick = {}
    )
}
