package com.example.voxel_review.data.dataSource.services

import com.example.voxel_review.data.dtos.UserDtoGeneric
import com.example.voxel_review.data.dtos.UserProfileRetrofitDto

import retrofit2.http.GET
import retrofit2.http.Path

interface UserRetrofitService {

	@GET("users/{userId}")
	suspend fun getUserById(@Path("userId") userId: String): UserProfileRetrofitDto

	@GET("users/")
	suspend fun getAllUsers(): List<UserProfileRetrofitDto>
}