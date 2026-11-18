package com.example.voxel_review.data.repository

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
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
    private val db: FirebaseFirestore,
    private val strings: AppStrings
){

    suspend fun uploadProfileImage(uri: Uri): Result<String> {
     return try{
         val userId = auth.currentUser?.uid ?: return Result.failure(Exception(strings[R.string.sign_in_profile_photo]))

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
         Result.failure(Exception(strings[R.string.error_profile_photo], e))
     }
    }
}
