
package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.R
import com.example.voxel_review.data.info.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.novedades.NovedadesState
import com.example.voxel_review.ui.screens.novedades.NovedadesViewModel

@Composable
fun NovedadesContent(
    novedadesViewModel: NovedadesViewModel,
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCategoriaSeleccionada: (String) -> Unit,
    onReviewClick: (ReviewInfo) -> Unit = {},
    onUserProfileClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val todo = "Todo"
    val rpg = "RPG"
    val accion = "Acción"
    val aventura = "Aventura"

    val state by novedadesViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .padding(top = 45.dp),
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.novedades_y_rese_as),
                color = Color.White,
                fontSize = 22.sp
            )


            Campana(
                modifier = Modifier.size(30.dp),
                onClick = onNotificationClick
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        FeedFilterSection(
            showOnlyFollowed = state.showOnlyFollowed,
            onFilterSelected = { enabled ->
                novedadesViewModel.toggleFollowedFilter(enabled)
            }
        )

        Spacer(modifier = Modifier.height(18.dp))


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
                text = state.error!!,
                color = Color.Red
            )

        } else if (state.showOnlyFollowed) {
            // Sección de contenido de publicaciones de usuarios seguidos
            FollowedReviewsSection(
                isLoading = state.isLoading,
                reviews = state.followedReviews,
                onReviewClick = onReviewClick,
                onUserProfileClick = onUserProfileClick
            )
        } else if (state.listaJuegos.isEmpty()) {
            Text(
                text = "No hay videojuegos disponibles.",
                color = Color.White
            )

        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
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
                        calificacion = "%.1f".format(juego.calificacion),
                        onClick = {
                            onClick(juego)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun NovedadesContentPreview() {
    NovedadesContent(
        novedadesViewModel = viewModel(),
        onCategoriaSeleccionada = {},
        onClick = {},
        onNotificationClick = {}
    )
}
