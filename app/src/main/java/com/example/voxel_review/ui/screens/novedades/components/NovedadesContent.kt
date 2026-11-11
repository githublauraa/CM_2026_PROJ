package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.info.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.novedades.NovedadesViewModel

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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .padding(top = 8.dp)
    ) {

        NovedadesHeader(
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros
        FeedFilterSection(
            selectedFilter = state.selectedFilter,
            onFilterSelected = { filter ->
                novedadesViewModel.selectFilter(filter)
            },
            onMapClick = onMapClick
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Contenido según el filtro seleccionado
        when (state.selectedFilter) {

            "Siguiendo" -> {

                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                } else if (state.error != null) {

                    Text(
                        text = state.error ?: "",
                        color = Color.Red
                    )

                } else {

                    FollowedReviewsSection(
                        isLoading = state.isLoading,
                        reviews = state.followedReviews,
                        onReviewClick = onReviewClick,
                        onUserProfileClick = onUserProfileClick
                    )
                }
            }

            "Todos" -> {

                if (state.isLoading) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                } else if (state.error != null) {

                    Text(
                        text = state.error ?: "",
                        color = Color.Red
                    )

                } else if (state.listaJuegos.isEmpty()) {

                    Text(
                        text = "No hay videojuegos disponibles.",
                        color = Color.White
                    )

                } else {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.listaJuegos,
                            key = { it.id }
                        ) { juego ->

                            TarjetaJuego(
                                imagen = juego.imagen,
                                etiqueta = juego.etiqueta,
                                nombre = juego.nombre,
                                descripcion = juego.descripcion,
                                autor = juego.autor,
                                calificacion = "%.1f".format(
                                    juego.calificacion
                                ),
                                onClick = {
                                    onClick(juego)
                                }
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )
                        }
                    }
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
