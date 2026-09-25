package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.ui.utils.ProfileImage

/**
 * Componente visual que muestra el banner promocional del juego con su nombre superpuesto.
 *
 * @param game Información detallada del juego actual.
 */
@Composable
fun GameBanner(
    game: GameDetailInfo
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {

        ProfileImage(
            profileImage = game.banner,
            size = 165,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = game.nombre.uppercase(),
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun GameBannerPreview() {

    val game = GameDetailInfo(
        banner = "",
        nombre = "STARFIELD",
        descripcion = "",
        desarrollador = "Bethesda",
        lanzamiento = "Oct 2023",
        generos = emptyList(),
        imagen = ""
    )

    GameBanner(
        game = game
    )
}