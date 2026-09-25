package com.example.voxel_review.data.review

data class ReviewInfo(
    val imagenJuego: String,
    val tituloJuego: String,
    val desarrollador: String,
    val tituloDescripcion: String,
    val descripcion: String,
    val ratingGeneral: Float,
    val ratingJugabilidad: Float,
    val ratingGraficos: Float,
    val ratingHistoria: Float,
    val imagenUsuario: String,
    val usernameReview: String,
    val comentarioReview: String,
    val ratingUsuario: Int
)