package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.signIn(email, password)
            Result.success(Unit)
        } catch (error: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No se encontró una cuenta activa con ese correo.", error))
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Revisa el correo y la contraseña.", error))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo iniciar sesión.", error))
        }
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.signUp(email, password)
            Result.success(Unit)
        } catch (error: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("El correo electrónico ya está en uso.", error))
        } catch (error: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Elige una contraseña más segura.", error))
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Revisa el formato del correo electrónico.", error))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo crear la cuenta.", error))
        }
    }

    fun logOut(): Result<Unit> {
        return try {
            authRemoteDataSource.signOut()
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo cerrar sesión.", error))
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            authRemoteDataSource.sendPasswordResetEmail(email)
            Result.success(Unit)
        } catch (error: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No existe una cuenta asociada a este correo.", error))
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Revisa el formato del correo electrónico.", error))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo enviar el correo de recuperación.", error))
        }
    }

    suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            authRemoteDataSource.sendEmailVerification()
            Result.success(Unit)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo enviar el correo de verificación.", error))
        }
    }

    suspend fun reloadAndCheckEmailVerified(): Result<Boolean> {
        return try {
            Result.success(authRemoteDataSource.reloadAndCheckEmailVerified())
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(Exception("No se pudo comprobar la verificación del correo.", error))
        }
    }
}
