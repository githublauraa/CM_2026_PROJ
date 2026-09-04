package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import com.example.voxel_review.ui.screens.GameDetail.GameDetailState
import com.example.voxel_review.ui.theme.backgroundDark

/**
 * Estructura y contenido visual completo de la pantalla de detalles de un juego.
 *
 * @param state Estado actual con los datos del juego y recomendados.
 * @param navController Controlador de navegación de Jetpack Compose.
 * @param onBackPressed Acción ejecutada al presionar el botón de regreso.
 * @param onSearchPressed Acción ejecutada al presionar el icono de búsqueda.
 * @param onWriteReviewPressed Acción ejecutada al presionar el botón de crear reseña.
 * @param modifier Modificador para personalizar el contenedor principal.
 */
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
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundDark)
    ) {

        // Top Bar
        GameDetailTopBar(
            onBackPressed = onBackPressed,
            onSearchPressed = onSearchPressed
        )

        if (game == null) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                // Contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 90.dp)
                ) {

                    GameHeader(
                        game = game
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    GameDescription(
                        text = "DESCRIPCIÓN",
                        description = game.descripcion
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    GameInformation(
                        developer = game.desarrollador,
                        releaseDate = game.lanzamiento
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    RecommendedGames(
                        games = state.recommendedGames
                    )
                }
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
