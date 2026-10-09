package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.voxel_review.data.review.ReviewInfo
import com.example.voxel_review.ui.theme.White

private const val GRID_COLUMNS = 3
private const val CARD_HEIGHT_DP = 170
private const val CARD_SPACING_DP = 12

/**
 * Muestra las reseñas del usuario como una cuadrícula de tarjetas con la imagen del juego.
 *
 * @param reviews reseñas a mostrar en la cuadrícula.
 * @param onReviewClick callback ejecutado al tocar una tarjeta, recibe la reseña seleccionada.
 */
@Composable
fun GameCards(
    reviews: List<ReviewInfo>,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    val rows = (reviews.size + GRID_COLUMNS - 1) / GRID_COLUMNS

    // La cuadrícula vive dentro de un Column con scroll vertical, así que necesita una
    // altura acotada: se calcula con el número de filas y el alto fijo de las tarjetas.
    val gridHeight =
        if (rows == 0) 0.dp else (rows * CARD_HEIGHT_DP + (rows - 1) * CARD_SPACING_DP).dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Mis Juegos",
            color = White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMNS),
            verticalArrangement = Arrangement.spacedBy(CARD_SPACING_DP.dp),
            horizontalArrangement = Arrangement.spacedBy(CARD_SPACING_DP.dp),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
        ) {
            items(
                items = reviews,
                key = { it.idResenia }
            ) { review ->
                GameCard(
                    review = review,
                    onClick = { onReviewClick(review) }
                )
            }
        }
    }
}

/**
 * Tarjeta individual de una reseña: muestra la imagen del juego y responde al toque.
 */
@Composable
fun GameCard(
    review: ReviewInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(CARD_HEIGHT_DP.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors()
    ) {

        GameCardImage(
            imagenJuego = review.imagenJuego,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Imagen del juego tomada de internet.
 *
 * Si [imagenJuego] está vacía o la descarga falla, se muestra la imagen de respaldo.
 */
@Composable
fun GameCardImage(
    imagenJuego: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imagenJuego.takeIf { it.isNotBlank() })
            .crossfade(true)
            .build(),

        contentDescription = "Imagen del juego",
        fallback = painterResource(id = R.drawable.imagen_login_user),
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.imagen_login_user),

        contentScale = ContentScale.Crop,
        modifier = modifier.clip(RoundedCornerShape(12.dp))
    )
}

@Preview
@Composable
fun GameCardPreview() {
    GameCard(
        review = ReviewInfo(
            idResenia = "1",
            imagenJuego = "",
            tituloJuego = "Chrono Sphere",
            desarrollador = "Voxel Studios",
            tituloDescripcion = "Una obra maestra",
            descripcion = "Una experiencia única.",
            ratingGeneral = 4.2f,
            ratingJugabilidad = 4.5f,
            ratingGraficos = 4.0f,
            ratingHistoria = 4.1f,
            imagenUsuario = ""
        ),
        onClick = {}
    )
}
