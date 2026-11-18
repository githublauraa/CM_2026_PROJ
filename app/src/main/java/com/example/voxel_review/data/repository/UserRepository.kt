package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.info.profile.ProfileInfo
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CancellationException

class UserRepository @Inject constructor(
	private val userRemoteDataSource: UserFirestoreDataSourceImpl,
	private val authRepository: AuthRepository
) {
	suspend fun getUserById(id: String): Result<ProfileInfo> {
		return try {
			val currentUserId = authRepository.currentUser?.uid.orEmpty()
			val user = userRemoteDataSource.getUserById(id, currentUserId)
			if (user == null) return Result.failure(Exception("No encontramos este perfil."))
			val userProfileInfo = user.toUserProfileinfo()
			Result.success(userProfileInfo)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception("No se pudo cargar el perfil.", e))
		}
	}

    suspend fun getFollowing(userId: String): Result<List<ProfileInfo>> {
        return try {
            Result.success(userRemoteDataSource.getFollowing(userId).map { it.toUserProfileinfo() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception("No se pudieron cargar las personas seguidas.", e))
        }
    }

    suspend fun getFollowers(userId: String): Result<List<ProfileInfo>> {
        return try {
            Result.success(userRemoteDataSource.getFollowers(userId).map { it.toUserProfileinfo() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception("No se pudieron cargar los seguidores.", e))
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
			Result.failure(Exception("No se pudo cargar la clasificación.", e))
		}
	}

	suspend fun registerUser(nombreUsuario: String, bio: String?,userId: String, photoUrl: String? = null) : Result<Unit> {
		return try {
			val fcmToken = FirebaseMessaging.getInstance().token.await()
			val registerUserDto = RegisterUserDto(nombreUsuario, bio, photoUrl, fcmToken)
			userRemoteDataSource.registerUser(registerUserDto, userId)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception("No se pudo guardar el perfil.", e))
		}
	}

	suspend fun updateUserBio(userId: String, bio: String): Result<Unit> {

		return try {
			userRemoteDataSource.updateUserBio(userId, bio)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception("No se pudo actualizar la biografía.", e))
		}

	}


	suspend fun followOrUnfollow(currentUserId: String, targetUserId: String): Result<Unit>{
		return try {
		    userRemoteDataSource.followOrUnfollow(currentUserId, targetUserId)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception("No se pudo actualizar el seguimiento.", e))
		}
	}

	suspend fun updateUsername(userId: String, username: String): Result<Unit>{

		return try {
			userRemoteDataSource.updateUsername(userId, username)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception("No se pudo actualizar el nombre de usuario.", e))
		}

	}

	suspend fun updateEmailVerified(userId: String, verified: Boolean): Result<Unit> {
		return try {
			userRemoteDataSource.updateEmailVerified(userId, verified)
			Result.success(Unit)
		} catch (cancellation: CancellationException) {
		    throw cancellation
		} catch (e: Exception) {
			Result.failure(Exception("No se pudo guardar la verificación del correo.", e))
		}
	}
}
