package com.example.voxel_review.data.repository

import android.util.Log
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.info.profile.ProfileInfo
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withTimeoutOrNull
import org.checkerframework.checker.units.qual.s

class UserRepository @Inject constructor(
	private val userRemoteDataSource: UserFirestoreDataSourceImpl,
	private val authRepository: AuthRepository
) {
	suspend fun getUserById(id: String): Result<ProfileInfo> {
		val currentUserId = authRepository.currentUser?.uid ?:""
		return try {
			val user = userRemoteDataSource.getUserById(id, currentUserId)
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
			val fcmToken = try {
                withTimeoutOrNull(5_000) { FirebaseMessaging.getInstance().token.await() }
            } catch (e: CancellationException) { throw e
            } catch (_: Exception) { null }
			val registerUserDto = RegisterUserDto(nombreUsuario, bio, photoUrl, fcmToken.orEmpty())
			userRemoteDataSource.registerUser(registerUserDto, userId)
			Result.success(Unit)
		} catch(e: CancellationException) {
            throw e
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


	suspend fun followOrUnfollow(currentUserId: String, targetUserId: String): Result<Unit>{
		return try {
		    userRemoteDataSource.followOrUnfollow(currentUserId, targetUserId)
			Result.success(Unit)
		} catch (e: Exception){
			Result.failure(e)
		}
	}

	suspend fun updateUsername(userId: String, username: String): Result<Unit>{
		return runCatching {
			userRemoteDataSource.updateUsername(userId, username)
		}
	}
}
