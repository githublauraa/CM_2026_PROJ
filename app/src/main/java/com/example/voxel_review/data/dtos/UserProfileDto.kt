package com.example.voxel_review.data.dtos

import com.example.voxel_review.data.profile.ProfileInfo

//para que funcione con express y con firestore

abstract class UserDtoGeneric{
	abstract fun toUserProfileinfo(): ProfileInfo
}

//retrofit con express por biography
data class UserProfileRetrofitDto(
	val userId: Int,
	val username: String,
	val biography: String? = null,
	val photoUrl: String? = null,
	val numReviews: Int,
	val likes: Int,
	val avgRating: Float,
): UserDtoGeneric(){
	constructor(): this(0, "", null, null, 0, 0, 0f)

	override fun toUserProfileinfo(): ProfileInfo {
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
}

data class UserProfileFirestoreDto(
	val userId: String,
	val username: String,
	val bio: String? = null,
	val photoUrl: String? = null,
	val numReviews: Int,
	val likes: Int,
	val avgRating: Float,
): UserDtoGeneric(){
	constructor(): this("", "", null, null, 0, 0, 0f)

	override fun toUserProfileinfo(): ProfileInfo {
		return ProfileInfo(
			id = userId,
			pfp = photoUrl ?: "No hay foto de perfil",
			nick = username,
			numResenias = numReviews,
			promedio = avgRating,
			likes = likes,
			biografia = bio ?: "No hay biografia",
		)
	}
}