
package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import com.example.voxel_review.ui.screens.GameDetail.GameDetailState
import com.example.voxel_review.ui.theme.backgroundDark

@Composable
fun GameDetailContent(
    state: GameDetailState,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val game = state.game

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundDark)
    ) {

        // Barra superior
        GameDetailTopBar(
            onBackPressed = onBackPressed,
            onSearchPressed = onSearchPressed
        )

        if (game == null) {

            // Estado de carga o error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator()
                    }

                    state.error != null -> {
                        Text(
                            text = state.error,
                            color = Color.White
                        )
                    }

                    else -> {
                        Text(
                            text = "No se encontró el videojuego",
                            color = Color.White
                        )
                    }
                }
            }

        } else {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                // Contenido principal con desplazamiento
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 90.dp)
                ) {

                    // Información principal del videojuego
                    GameHeader(
                        game = game
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // Descripción
                    GameDescription(
                        text = "DESCRIPCIÓN",
                        description = game.descripcion
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    // Información adicional
                    GameInformation(
                        developer = game.desarrollador,
                        releaseDate = game.lanzamiento
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    // Sección de reseñas
                    Text(
                        text = "RESEÑAS DE USUARIOS",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    if (state.isLoading) {

                        CircularProgressIndicator()

                    } else if (state.reviews.isEmpty()) {

                        Text(
                            text = if (state.error != null) {
                                "No se pudieron cargar las reseñas: ${state.error}"
                            } else {
                                "Este videojuego todavía no tiene reseñas."
                            },
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )

                    } else {

                        // Mostrar todas las reseñas del videojuego
                        state.reviews.forEach { review ->

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {

                                Text(
                                    text = review.tituloDescripcion,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text = review.descripcion,
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text = "Calificación: ${review.ratingGeneral}/5",
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // Videojuegos recomendados
                    RecommendedGames(
                        games = state.recommendedGames
                    )
                }

                // Botón flotante para escribir reseña
                WriteReviewButton(
                    onClick = onWriteReviewPressed,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun GameDetailContentPreview() {

    GameDetailContent(
        state = GameDetailState(
            game = LocalGameProvider.starfield,
            recommendedGames = LocalGameRecomendedProvider.recommendedGames
        ),
        onBackPressed = {},
        onSearchPressed = {},
        onWriteReviewPressed = {}
    )
}
