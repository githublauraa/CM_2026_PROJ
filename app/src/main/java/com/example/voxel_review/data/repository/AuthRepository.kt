package com.example.voxel_review.data.repository

import jakarta.inject.Inject
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuthUserCollisionException
class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser

    suspend fun signIn(email:String, password: String): Result<Unit>{
        try {
            authRemoteDataSource.signIn(email,password)
            return Result.success(Unit)
        }
        catch (e: FirebaseAuthInvalidUserException){
            return Result.failure(Exception("Usuario no existe"))
        }
        catch (e: FirebaseAuthInvalidCredentialsException){
            return Result.failure(Exception("Credenciales incorrectas"))
        }
        catch (e: Exception){
            return Result.failure(e)
        }

    }

    suspend fun signUp(email: String, password: String): Result<Unit>{
        try{
            authRemoteDataSource.signUp(email,password)
            return Result.success(Unit)
        }catch (e: FirebaseAuthUserCollisionException) {
            return Result.failure(Exception("Ya existe una cuenta con este correo"))
        }
        catch (e: Exception){
            return Result.failure(Exception("Error al iniciar sesion"))
        }

    }

    fun logOut(){
        authRemoteDataSource.signOut()
    }
}