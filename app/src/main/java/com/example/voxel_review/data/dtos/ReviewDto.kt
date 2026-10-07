package com.example.voxel_review.data.dtos

data class ReviewDto(
	val idResenia: Int,
	val idUsuario: Int,
	val idVideoJuego: Int,
	val calificacion: Int,
	val fechaResenia: String,
	val contenido: String,
)
