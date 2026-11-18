package com.example.voxel_review.data.repository

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val strings: AppStrings
) {
    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.signIn(email, password)
            Result.success(Unit)
        } catch (error: FirebaseAuthInvalidUserException) {
            Result.failure(Exception(strings[R.string.active_account_not_found], error))
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception(strings[R.string.check_login_credentials], error))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception(strings[R.string.error_sign_in], error))
        }
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.signUp(email, password)
            Result.success(Unit)
        } catch (error: FirebaseAuthUserCollisionException) {
            Result.failure(Exception(strings[R.string.email_already_used], error))
        } catch (error: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception(strings[R.string.choose_stronger_password], error))
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception(strings[R.string.check_email_format], error))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception(strings[R.string.error_create_account], error))
        }
    }

    fun logOut(): Result<Unit> {
        return try {
            authRemoteDataSource.signOut()
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(Exception(strings[R.string.error_sign_out], error))
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            authRemoteDataSource.sendPasswordResetEmail(email)
            Result.success(Unit)
        } catch (error: FirebaseAuthInvalidUserException) {
            Result.failure(Exception(strings[R.string.no_email_account], error))
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception(strings[R.string.check_email_format], error))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception(strings[R.string.error_reset_email], error))
        }
    }

    suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            authRemoteDataSource.sendEmailVerification()
            Result.success(Unit)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception(strings[R.string.error_email_verification], error))
        }
    }

    suspend fun reloadAndCheckEmailVerified(): Result<Boolean> {
        return try {
            Result.success(authRemoteDataSource.reloadAndCheckEmailVerified())
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception(strings[R.string.error_check_email_verification], error))
        }
    }
}
