package com.example.voxel_review.data.repository

import android.net.Uri
import javax.inject.Inject
import com.example.voxel_review.data.dataSource.StorageRemoteDataSource
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource,
    private val db: FirebaseFirestore
){

    suspend fun uploadProfileImage(uri: Uri): Result<String> {
     return try{
         val userId = auth.currentUser?.uid ?: return Result.failure(Exception("No user logged in"))

         val path = "profile/$userId.jpg"
         val url = storage.uploadImage(path, uri)

         db.collection("users")
             .document(userId)
             .update("photoUrl", url)
             .await()

         auth.updateProfileImage(url)

         Result.success(url)
     }
     catch (e: Exception){
         Result.failure(e)
     }
    }
}