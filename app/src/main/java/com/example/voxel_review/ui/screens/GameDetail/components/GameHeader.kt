package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.InfoGame.GameDetailInfo

/**
 * Encabezado de la pantalla de detalle que combina el banner e indicadores de géneros.
 *
 * @param game Información del juego utilizada para construir el encabezado.
 */
@Composable
fun GameHeader(
    game: GameDetailInfo
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        GameBanner(
            game = game
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        GameGenres(
            genres = game.generos
        )
    }
}

@Preview
@Composable
fun GameHeaderPreview() {

    val game = GameDetailInfo(
        banner = R.drawable.banner_games,
        nombre = "STARFIELD",
        descripcion = "",
        desarrollador = "Bethesda",
        lanzamiento = "Oct 2023",
        generos = listOf(
            LocalGenreProvider.generos.first { it.id == "1" },
            LocalGenreProvider.generos.first { it.id == "2" },
            LocalGenreProvider.generos.first { it.id == "3" },
        ),
        imagen = R.drawable.banner_games
    )

    GameHeader(
        game = game
    )
}