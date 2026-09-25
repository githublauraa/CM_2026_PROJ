package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.ui.utils.ProfileImage

/**
 * Tarjeta individual que muestra la imagen de portada de un juego recomendado.
 *
 * @param game Información del juego recomendado.
 */
@Composable
fun RecommendedGameCard(
    game: GameDetailInfo
) {

    ProfileImage(
        profileImage = game.imagen,
        size = 72,
        modifier = Modifier
            .size(
                width = 72.dp,
                height = 72.dp
            )
            .clip(RoundedCornerShape(12.dp))
    )
}

@Preview
@Composable
fun RecommendedGameCardPreview() {

    val game = GameDetailInfo(
        banner = "",
        nombre = "Mass Effect",
        descripcion = "",
        desarrollador = "",
        lanzamiento = "",
        generos = emptyList(),
        imagen = ""
    )

    RecommendedGameCard(
        game = game
    )
}