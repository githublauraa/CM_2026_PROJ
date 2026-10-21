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
import com.example.voxel_review.ui.theme.VoxelSurface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.review.DescriptionSection
import com.example.voxel_review.ui.screens.review.HeroSection
import com.example.voxel_review.ui.screens.review.RatingCard
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.review.ReviewDetailState
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
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
    onReviewProfileImageClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    onLikeClick: (String) -> Unit = {}
) {



    val cardBackground =
        VoxelSurface

    val review = state.review


    LaunchedEffect(review?.imagenUsuario) {
        Log.d("PROFILE_IMAGE", "Imagen del usuario: ${review?.imagenUsuario}")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(review != null) {
            HeroSection(
                imagenJuego = review.imagenJuego,
                tituloJuego = review.tituloJuego,
                desarrollador = review.desarrollador,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            RatingCard(
                ratingGeneral = review.ratingGeneral,
                ratingJugabilidad = review.ratingJugabilidad,
                ratingGraficos = review.ratingGraficos,
                ratingHistoria = review.ratingHistoria,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                cardBackgroundColor = cardBackground
            )

            DescriptionSection(
                titulo = review.tituloDescripcion,
                descripcion = review.descripcion,
                imagenUsuario = review.imagenUsuario,
                likes = review.cantidadLikes,
                isliked = review.likedByCurrentUser,
                onAuthorClick = { onReviewProfileImageClicked(review.userId) },
                onLikeClick = { onLikeClick(review.idResenia) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(
                modifier = Modifier.height(96.dp)
            )
        }
    }
}

@Composable
@Preview
fun ReviewDetailContentPreview() {

    ReviewDetailContent(
        state = ReviewDetailState(
            review = ReviewInfo(
                idResenia = "preview",
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
            )
        ),
        onClickReview = {},
        onCommentClick = {}
    )
}
