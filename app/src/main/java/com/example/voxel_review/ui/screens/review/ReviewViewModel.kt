package com.example.voxel_review.ui.screens.review

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.review.LocalReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState.asStateFlow()

    init {
        loadReview()
    }

    private fun loadReview() {

        val review = LocalReviewProvider.reviews.firstOrNull()

        if (review != null) {

            _uiState.update {
                it.copy(
                    tituloJuego = review.tituloJuego,
                    desarrollador = review.desarrollador,
                    tituloDescripcion = review.tituloDescripcion,
                    descripcion = review.descripcion,

                    ratingGeneral = review.ratingGeneral,
                    ratingJugabilidad = review.ratingJugabilidad,
                    ratingGraficos = review.ratingGraficos,
                    ratingHistoria = review.ratingHistoria,

                    usernameReview = review.usernameReview,
                    comentarioReview = review.comentarioReview,
                    ratingUsuario = review.ratingUsuario,

                    isLoading = false,
                    errorMessage = null
                )
            }

        } else {

            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "No se pudo encontrar la reseña"
                )
            }
        }
    }
}