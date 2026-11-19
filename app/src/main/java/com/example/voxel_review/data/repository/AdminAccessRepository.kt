package com.example.voxel_review.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdminAccessRepository @Inject constructor(private val auth: FirebaseAuth) {
    suspend fun isAdmin(): Boolean {
        val user = auth.currentUser ?: return false
        val token = user.getIdToken(true).await()
        return auth.currentUser?.uid == user.uid && token.claims["admin"] == true
    }

    suspend fun requireAdmin() {
        check(isAdmin()) { "Administrator access required" }
    }
}
