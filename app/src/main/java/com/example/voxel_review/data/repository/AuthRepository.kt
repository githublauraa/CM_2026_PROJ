package com.example.voxel_review.data.repository

import jakarta.inject.Inject
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser

    suspend fun signIn(email:String, password: String){
        authRemoteDataSource.signIn(email,password)
    }

    suspend fun signUp(email: String, password: String){
        authRemoteDataSource.signUp(email,password)
    }

    fun singOut(){
        authRemoteDataSource.signOut()
    }
}