package com.example.voxel_review.data.repository

import jakarta.inject.Inject
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CancellationException

class AuthRepository @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource) {
    val currentUser: FirebaseUser? get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): Result<Unit> = authenticate {
        authRemoteDataSource.signIn(email.trim(), password)
    }

    suspend fun signUp(email: String, password: String): Result<Unit> = authenticate {
        authRemoteDataSource.signUp(email.trim(), password)
    }

    private suspend fun authenticate(action: suspend () -> Unit): Result<Unit> = try {
        action()
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun logOut() = authRemoteDataSource.signOut()
}
