package com.example.voxel_review.ui.screens.review.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.data.review.LocalReviewProvider.reviews
import com.example.voxel_review.ui.screens.review.DescriptionSection
import com.example.voxel_review.ui.screens.review.HeroSection
import com.example.voxel_review.ui.screens.review.RatingCard
import com.example.voxel_review.ui.screens.review.ReviewDetailState
import com.example.voxel_review.ui.screens.review.UserReviewsSection

/**
 * Contenido estructurado deslizable que integra las secciones de la reseña detallada de un juego.
 *
 * @param state Estado actual de la pantalla de detalle de reseña.
 * @param onClickReview Callback ejecutado al interactuar con las reseñas.
 * @param modifier Modificador para personalizar el scroll o layout principal.
 */
@Composable
fun ReviewDetailContent(
    state: ReviewDetailState,
    onClickReview: () -> Unit,
    onCommentClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val cardBackground =
        colorResource(R.color.voxel_background).copy(alpha = 0.5f)

    val review = state.reviews.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(review != null) {
            HeroSection(
                imagenJuego = state.reviews[0].imagenJuego,
                tituloJuego = state.reviews[0].tituloJuego,
                desarrollador = state.reviews[0].desarrollador,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            RatingCard(
                ratingGeneral = state.reviews[0].ratingGeneral,
                ratingJugabilidad = state.reviews[0].ratingJugabilidad,
                ratingGraficos = state.reviews[0].ratingGraficos,
                ratingHistoria = state.reviews[0].ratingHistoria,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                cardBackgroundColor = cardBackground
            )

            DescriptionSection(
                titulo = state.reviews[0].tituloDescripcion,
                descripcion = state.reviews[0].descripcion,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            UserReviewsSection(
                imagenUsuario = review.imagenUsuario,
                username = review.tituloDescripcion.removePrefix("Reseña por"), // Usa review.userId o la propiedad adecuada para el nombre
                comentario = review.descripcion, // Se mapea la propiedad descripcion de ReviewInfo
                rating = review.ratingGeneral.toInt(),
                onClickReview = onClickReview,
                onCommentClick = {
                    onCommentClick(review.idResenia) // Se usa videoGameId del data class
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                cardBackgroundColor = cardBackground
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}

@Composable
@Preview
fun ReviewDetailContentPreview() {

    ReviewDetailContent(
        state = ReviewDetailState(
            imagenJuego = "",
            tituloJuego = "Chrono Sphere",
            desarrollador = "Desarrollado por Voxel Studios",
            tituloDescripcion = "Una obra maestra",
            descripcion = "Una experiencia increíble con una excelente historia.",
            ratingGeneral = 4.2f,
            ratingJugabilidad = 4.5f,
            ratingGraficos = 4.0f,
            ratingHistoria = 4.1f,
            imagenUsuario = "",
        ),
        onClickReview = {},
        onCommentClick = {}
    )
}