package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dataSource.impl.UserRetrofitDataSourceImpl
import com.example.voxel_review.data.dtos.toProfileInfo
import com.example.voxel_review.data.profile.ProfileInfo
import javax.inject.Inject

class UserRepository @Inject constructor(private val userRemoteDataSource: UserRetrofitDataSourceImpl) {
	suspend fun getUserById(id: String): Result<ProfileInfo> {
		return try {
			val user = userRemoteDataSource.getUserById(id)
			val userProfileInfo = user.toProfileInfo()
			Result.success(userProfileInfo)
		} catch(e: Exception){
			Result.failure(e)
		}
	}

	suspend fun getAllUsers(): Result<List<ProfileInfo>> {
		return try {
			val users = userRemoteDataSource.getAllUsers()
			val userProfileInfo = users.map { it.toProfileInfo() }
			Result.success(userProfileInfo)
		} catch(e: Exception){
			Result.failure(e)
		}
	}
}
