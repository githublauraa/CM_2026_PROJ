package com.example.voxel_review.data.dtos

import com.example.voxel_review.data.review.ReviewInfo

data class ReviewDto(
	val idResenia: Int,
	val idUsuario: Int,
	val idVideoJuego: Int,
	val ratingJugabilidad: Int,
	val ratingGraficos: Int,
	val ratingHistoria: Int,
	val contenido: String,
	val createdAt: String,
	val updatedAt: String,
	val user: UserDto,
	val videoGame: VideoGameDto
)

data class UserDto(
	val nombreUsuario: String,
	val fotoUrl: String?
)

data class VideoGameDto(
	val nombre: String,
	val desarrollador: String,
	val fotoUrl: String?
)

fun ReviewDto.toReviewInfo(): ReviewInfo {
	return ReviewInfo(
		imagenJuego = videoGame.fotoUrl ?: "",
		tituloJuego = videoGame.nombre,
		desarrollador = videoGame.desarrollador,
		tituloDescripcion = "Reseña por ${user.nombreUsuario}",
		descripcion = contenido,
		ratingGeneral = ((ratingJugabilidad + ratingGraficos + ratingHistoria) / 3.0f),
		ratingJugabilidad = ratingJugabilidad.toFloat(),
		ratingGraficos = ratingGraficos.toFloat(),
		ratingHistoria = ratingHistoria.toFloat(),
		imagenUsuario = user.fotoUrl ?: "",
		usernameReview = user.nombreUsuario,
		comentarioReview = contenido,
		ratingUsuario = ((ratingJugabilidad + ratingGraficos + ratingHistoria) / 3.0f).toInt()
	)
}
