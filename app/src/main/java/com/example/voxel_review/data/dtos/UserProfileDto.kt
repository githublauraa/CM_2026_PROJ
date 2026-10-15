package com.example.voxel_review.data.dtos

import com.example.voxel_review.data.profile.ProfileInfo

data class UserProfileDto(
	val userId: Int,
	val username: String,
	val biography: String? = null,
	val photoUrl: String? = null,
	val numReviews: Int,
	val likes: Int,
	val avgRating: Float,
){
	constructor(): this(0, "", null, null, 0, 0, 0f)
}

fun UserProfileDto.toProfileInfo(): ProfileInfo {
	return ProfileInfo(
		id = userId.toString(),
		pfp = photoUrl ?: "No hay foto de perfil",
		nick = username,
		numResenias = numReviews,
		promedio = avgRating,
		likes = likes,
		biografia = biography ?: "No hay biografia",
	)
}