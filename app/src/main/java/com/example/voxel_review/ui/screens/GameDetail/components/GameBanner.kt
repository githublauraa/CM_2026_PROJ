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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.voxel_review.R
import com.example.voxel_review.data.info.InfoGame.GameDetailInfo

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

        // Foto de Benedict Calano (Unsplash): https://unsplash.com/photos/WBfhrZA2TAE
        val fallback = painterResource(R.drawable.game_banner_fallback)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.banner.takeIf { it.isNotBlank() })
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.loading_img),
            fallback = fallback,
            error = fallback,
            contentScale = ContentScale.Crop,
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
        id = "1",
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
