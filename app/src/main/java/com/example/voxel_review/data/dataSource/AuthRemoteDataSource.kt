package com.example.voxel_review.data.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import android.net.Uri
import com.google.firebase.auth.UserProfileChangeRequest

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {

    val currentUser: FirebaseUser?
        get() = auth.currentUser



    suspend fun signIn(email: String, password: String): Unit {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(email: String, password: String): Unit {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun sendPasswordResetEmail(email: String): Unit {
        auth.sendPasswordResetEmail(email).await()
    }

    suspend fun sendEmailVerification(): Unit {
        currentUser?.sendEmailVerification()?.await()
    }

    suspend fun reloadAndCheckEmailVerified(): Boolean {
        currentUser?.reload()?.await()
        return currentUser?.isEmailVerified == true
    }

    suspend fun updateProfileImage(photoUrl: String): Unit {
        val uri = Uri.parse(photoUrl)

        currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
        )?.await()
    }
}