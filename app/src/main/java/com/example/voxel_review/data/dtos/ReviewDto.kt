package com.example.voxel_review.data.dtos

import com.example.voxel_review.data.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.data.review.ReviewInfo

data class ReviewDto(
	val reviewId: Int,
	val userId: Int,
	val videoGameId: Int,
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
	val videoGameId: Int,
	val name: String,
	val description: String,
	val developer: String,
	val platform: String,
	val releaseDate: String,
	val imageUrl: String?
)

fun ReviewDto.toReviewInfo(): ReviewInfo {
	return ReviewInfo(
		idResenia = reviewId.toString(),
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
		userId = userId.toString(),
		videoGameId = videoGameId.toString(),
	)
}

fun VideoGameDto.toTrendingSearchInfo(): TrendingSearchInfo {
	return TrendingSearchInfo(
		id = videoGameId.toString(),
		nombre = name,
		categoria = platform
	)
}
