package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserProfileDto

interface UserRemoteDataSource {
	suspend fun getUserById(id: String): UserProfileDto
	suspend fun getAllUsers(): List<UserProfileDto>

	//guardar un usario en la BD
	suspend fun registerUser(registerUser : RegisterUserDto, id: String): Unit

}
