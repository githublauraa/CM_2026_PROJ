package com.example.voxel_review.ui.screens.review

import com.example.voxel_review.data.review.ReviewInfo

data class ReviewDetailState(
    val imagenJuego: String = "",
    val tituloJuego: String = "",
    val desarrollador: String = "",
    val tituloDescripcion: String = "",
    val descripcion: String = "",

    val ratingGeneral: Float = 0f,
    val ratingJugabilidad: Float = 0f,
    val ratingGraficos: Float = 0f,
    val ratingHistoria: Float = 0f,

    val imagenUsuario: String = "",
    val usernameReview: String = "",
    val comentarioReview: String = "",
    val ratingUsuario: Int = 0,

    val reviews: List<ReviewInfo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)