package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoGame.GameDetailInfo

/**
 * Tarjeta individual que muestra la imagen de portada de un juego recomendado.
 *
 * @param game Información del juego recomendado.
 */
@Composable
fun RecommendedGameCard(
    game: GameDetailInfo
) {

    Image(
        painter = painterResource(id = game.imagen),
        contentDescription = game.nombre,
        modifier = Modifier
            .size(
                width = 72.dp,
                height = 72.dp
            )
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}
@Preview
@Composable
fun RecommendedGameCardPreview() {

    val game = GameDetailInfo(
        banner = R.drawable.reco_game,
        nombre = "Mass Effect",
        descripcion = "",
        desarrollador = "",
        lanzamiento = "",
        generos = emptyList(),
        imagen = R.drawable.reco_game
    )

    RecommendedGameCard(
        game = game
    )
}