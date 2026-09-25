package com.example.voxel_review.data.repository

import android.net.Uri
import javax.inject.Inject
import com.example.voxel_review.data.dataSource.StorageRemoteDataSource
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource
){

    suspend fun uploadProfileImage(uri: Uri): Result<String> {
     return try{
         val userId = auth.currentUser?.uid ?: return Result.failure(Exception("No user logged in"))

         val path: String = "profile/$userId.jpg"
         val url = storage.uploadImage(path, uri)
         //actualizar url del usuario
         auth.updateProfileImage(url)
         Result.success(url)
     }
     catch (e: Exception){
         Result.failure(e)
     }
    }
}