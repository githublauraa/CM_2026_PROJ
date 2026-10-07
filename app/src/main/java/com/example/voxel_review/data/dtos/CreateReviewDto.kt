package com.example.voxel_review.data.dtos

data class CreateReviewDto(
    val idUsuario: Int,
    val idVideoJuego: Int,
    val ratingJugabilidad: Int,
    val ratingGraficos: Int,
    val ratingHistoria: Int,
    val contenido: String
)
