package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.LibraryRemoteDataSource
import com.example.voxel_review.data.dtos.LibraryEntryDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

/**
 * Implementación en Cloud Firestore de [LibraryRemoteDataSource] usando la subcolección
 * `users/{userId}/library/{videoGameId}`.
 */
class LibraryFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : LibraryRemoteDataSource {

    override suspend fun getUserLibrary(userId: String): List<LibraryEntryDto> {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("library")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(LibraryEntryDto::class.java)?.let { entry ->
                if (entry.videoGameId.isBlank()) entry.copy(videoGameId = doc.id) else entry
            }
        }.sortedByDescending { it.updatedAt }
    }

    override suspend fun getGameEntry(userId: String, videoGameId: String): LibraryEntryDto? {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("library")
            .document(videoGameId)
            .get()
            .await()

        if (!snapshot.exists()) return null

        return snapshot.toObject(LibraryEntryDto::class.java)?.let { entry ->
            if (entry.videoGameId.isBlank()) entry.copy(videoGameId = snapshot.id) else entry
        }
    }

    override suspend fun upsertLibraryEntry(userId: String, entry: LibraryEntryDto) {
        db.collection("users")
            .document(userId)
            .collection("library")
            .document(entry.videoGameId)
            .set(entry)
            .await()
    }

    override suspend fun deleteLibraryEntry(userId: String, videoGameId: String) {
        db.collection("users")
            .document(userId)
            .collection("library")
            .document(videoGameId)
            .delete()
            .await()
    }
}
