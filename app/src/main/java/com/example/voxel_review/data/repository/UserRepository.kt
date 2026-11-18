package com.example.voxel_review.data.repository

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.info.profile.ProfileInfo
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
import kotlinx.coroutines.CancellationException

class UserRepository @Inject constructor(
	private val userRemoteDataSource: UserFirestoreDataSourceImpl,
	private val authRepository: AuthRepository,
	private val strings: AppStrings
) {
	suspend fun getUserById(id: String): Result<ProfileInfo> {
		return try {
			val currentUserId = authRepository.currentUser?.uid.orEmpty()
			val user = userRemoteDataSource.getUserById(id, currentUserId)
			if (user == null) return Result.failure(Exception(strings[R.string.profile_not_found]))
			val userProfileInfo = user.toUserProfileinfo()
			Result.success(userProfileInfo)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_profile], e))
		}
	}

    suspend fun getFollowing(userId: String): Result<List<ProfileInfo>> {
        return try {
            Result.success(userRemoteDataSource.getFollowing(userId).map { it.toUserProfileinfo() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_following], e))
        }
    }

    suspend fun getFollowers(userId: String): Result<List<ProfileInfo>> {
        return try {
            Result.success(userRemoteDataSource.getFollowers(userId).map { it.toUserProfileinfo() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_followers], e))
        }
    }

	suspend fun getAllUsers(): Result<List<ProfileInfo>> {
		return try {
			val users = userRemoteDataSource.getAllUsers()
			val userProfileInfo = users.map { it.toUserProfileinfo() }
			Result.success(userProfileInfo)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_rankings], e))
		}
	}

	suspend fun registerUser(nombreUsuario: String, bio: String?,userId: String, photoUrl: String? = null) : Result<Unit> {
		return try {
			val registerUserDto = RegisterUserDto(nombreUsuario, bio, photoUrl)
			userRemoteDataSource.registerUser(registerUserDto, userId)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_profile_save], e))
		}
	}

	suspend fun updateUserBio(userId: String, bio: String): Result<Unit> {

		return try {
			userRemoteDataSource.updateUserBio(userId, bio)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_bio], e))
		}

	}


	suspend fun followOrUnfollow(currentUserId: String, targetUserId: String): Result<Unit>{
		return try {
		    userRemoteDataSource.followOrUnfollow(currentUserId, targetUserId)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_follow], e))
		}
	}

	suspend fun updateUsername(userId: String, username: String): Result<Unit>{

		return try {
			userRemoteDataSource.updateUsername(userId, username)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_username], e))
		}

	}

	suspend fun updateEmailVerified(userId: String, verified: Boolean): Result<Unit> {
		return try {
			userRemoteDataSource.updateEmailVerified(userId, verified)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception(strings[R.string.error_save_verification], e))
		}
	}
}
