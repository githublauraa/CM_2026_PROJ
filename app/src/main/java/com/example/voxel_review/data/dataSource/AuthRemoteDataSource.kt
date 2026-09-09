package com.example.voxel_review.data.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val auth : FirebaseAuth
){
    val currentUser: FirebaseUser? = auth.currentUser

    suspend fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
    }

    suspend fun signUp(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut(){
        auth.signOut()
    }

}