package com.example.voxel_review.data.dataSource.services

import com.example.voxel_review.data.dtos.UserProfileDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRetrofitService {

	@GET("users/{userId}")
	suspend fun getUserById(@Path("userId") userId: String): UserProfileDto

	@GET("users/")
	suspend fun getAllUsers(): List<UserProfileDto>
}