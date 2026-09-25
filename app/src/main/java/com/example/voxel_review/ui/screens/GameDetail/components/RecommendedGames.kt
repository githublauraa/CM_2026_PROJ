package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.InfoGame.GameDetailInfo

/**
 * Sección horizontal que muestra una lista deslizable con juegos recomendados similares.
 *
 * @param games Lista de juegos recomendados a mostrar.
 */
@Composable
fun RecommendedGames(
    games: List<GameDetailInfo>
) {

    Column {
        Text(
            text = "También Recomendado",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(games) { game ->

                RecommendedGameCard(
                    game = game
                )
            }

            item {
                MoreGamesCard()
            }
        }
    }
}

@Preview
@Composable
fun RecommendedGamesPreview() {

    val games = listOf(

        GameDetailInfo(
            banner = "",
            nombre = "Mass Effect",
            descripcion = "",
            desarrollador = "",
            lanzamiento = "",
            generos = emptyList(),
            imagen = ""
        ),

        GameDetailInfo(
            banner = "",
            nombre = "Outer Wilds",
            descripcion = "",
            desarrollador = "",
            lanzamiento = "",
            generos = emptyList(),
            imagen = ""
        ),

        GameDetailInfo(
            banner = "",
            nombre = "No Man's Sky",
            descripcion = "",
            desarrollador = "",
            lanzamiento = "",
            generos = emptyList(),
            imagen = ""
        )
    )

    RecommendedGames(
        games = games
    )
}