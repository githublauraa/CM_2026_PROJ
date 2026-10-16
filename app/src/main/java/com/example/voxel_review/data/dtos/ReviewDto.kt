package com.example.voxel_review.data.dtos

import com.example.voxel_review.data.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.data.review.ReviewInfo
import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo

data class ReviewDto(
	val reviewId: String,
	val userId: String,
	val videoGameId: String,
	val avgRating: Float,
	val gameplayRating: Int,
	val graphicsRating: Int,
	val storyRating: Int,
	val content: String,
	val createdAt: String,
	val updatedAt: String,
	val user: UserDto,
	val videoGame: VideoGameDto
)

data class UserDto(
	val username: String,
	val photoUrl: String?
)

data class VideoGameDto(
	val videoGameId: String,
	val name: String,
	val description: String,
	val developer: String,
	val platform: String,
	val releaseDate: String,
	val imageUrl: String?
){
	constructor() : this(
		videoGameId = "",
		name = "",
		description = "",
		developer = "",
		platform = "",
		releaseDate = "",
		imageUrl = null
	)
}

fun ReviewDto.toReviewInfo(): ReviewInfo {
	return ReviewInfo(
		idResenia = reviewId,
		imagenJuego = videoGame.imageUrl ?: "",
		tituloJuego = videoGame.name,
		desarrollador = videoGame.developer,
		tituloDescripcion = "Reseña por ${user.username}",
		descripcion = content,
		ratingGeneral = avgRating,
		ratingJugabilidad = gameplayRating.toFloat(),
		ratingGraficos = graphicsRating.toFloat(),
		ratingHistoria = storyRating.toFloat(),
		imagenUsuario = user.photoUrl ?: "",
		userId = userId,
		videoGameId = videoGameId,
	)
}

fun VideoGameDto.toTrendingSearchInfo(): TrendingSearchInfo {
	return TrendingSearchInfo(
		id = videoGameId,
		nombre = name,
		categoria = platform,
		imagen = imageUrl ?: ""
	)
}


fun VideoGameDto.toGameDetailInfo(): GameDetailInfo {
	return GameDetailInfo(
		id = videoGameId,
		banner = imageUrl ?: "",
		nombre = name,
		descripcion = description,
		desarrollador = developer,
		lanzamiento = releaseDate,
		generos = emptyList(),
		imagen = imageUrl ?: ""
	)
}

fun VideoGameDto.toJuegoInfo(): JuegoInfo {
	return JuegoInfo(
		id = videoGameId,
		imagen = imageUrl ?: "",
		etiqueta = "VIDEOJUEGO",
		nombre = name,
		descripcion = description,
		autor = developer,
		calificacion = null
	)
}