package com.example.voxel_review.data.repository

import android.util.Log
import com.example.voxel_review.data.dataSource.impl.UserRetrofitDataSourceImpl
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.profile.ProfileInfo
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
class UserRepository @Inject constructor(
	private val userRemoteDataSource: UserFirestoreDataSourceImpl
) {
	suspend fun getUserById(id: String): Result<ProfileInfo> {
		return try {
			val user = userRemoteDataSource.getUserById(id)
			val userProfileInfo = user.toUserProfileinfo()
			Result.success(userProfileInfo)
		} catch(e: Exception){
			Result.failure(e)
		}
	}

	suspend fun getAllUsers(): Result<List<ProfileInfo>> {
		return try {
			val users = userRemoteDataSource.getAllUsers()
			val userProfileInfo = users.map { it.toUserProfileinfo() }
			Log.d("UserRepository", "getAllUsers: ${userProfileInfo}")
			Result.success(userProfileInfo)
		} catch(e: Exception){
			Log.e("UserRepository", "getAllUsers: ${e.message}")
			Result.failure(e)
		}
	}

	suspend fun registerUser(nombreUsuario: String, bio: String?,userId: String, photoUrl: String? = null) : Result<Unit> {
		return try {
			val registerUserDto = RegisterUserDto(nombreUsuario, bio, photoUrl)
			userRemoteDataSource.registerUser(registerUserDto, userId)
			Result.success(Unit)
		} catch(e: Exception) {
			Result.failure(e)
		}
	}

	suspend fun updateUserBio(
		userId: String,
		bio: String
	): Result<Unit> {
		return runCatching {
			userRemoteDataSource.updateUserBio(userId, bio)
		}
	}
}
