package com.example.voxel_review.data.repository

import android.net.Uri
import javax.inject.Inject
import com.example.voxel_review.data.dataSource.StorageRemoteDataSource
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CancellationException

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource,
    private val db: FirebaseFirestore
){

    suspend fun uploadProfileImage(uri: Uri): Result<String> {
     return try{
         val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Inicia sesión para cambiar la foto de perfil."))

         val path = "profile/$userId.jpg"
         val url = storage.uploadImage(path, uri)

         db.collection("users")
             .document(userId)
             .update("photoUrl", url)
             .await()

         auth.updateProfileImage(url)

         Result.success(url)
     } catch (cancellation: CancellationException) {
         throw cancellation
     } catch (e: Exception) {
         Result.failure(Exception("No se pudo actualizar la foto de perfil.", e))
     }
    }
}
