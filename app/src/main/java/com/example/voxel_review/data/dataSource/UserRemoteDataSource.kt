package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.UserProfileDto

interface UserRemoteDataSource {
	suspend fun getUserById(id: String): UserProfileDto
}
