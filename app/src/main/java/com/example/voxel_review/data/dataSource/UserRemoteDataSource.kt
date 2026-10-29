package com.example.voxel_review.data.dataSource

import android.R
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserDtoGeneric

interface UserRemoteDataSource {
	suspend fun getUserById(id: String, currentUserId: String): UserDtoGeneric?
	suspend fun getAllUsers(): List<UserDtoGeneric>

	//guardar un usario en la BD
	suspend fun registerUser(registerUser : RegisterUserDto, id: String): Unit


	suspend fun updateUserBio(userId: String, bio: String)

	suspend fun updateUsername(userdId: String, username: String)

	suspend fun followOrUnfollow(currentUserId: String, targetUserId: String): Unit
}
