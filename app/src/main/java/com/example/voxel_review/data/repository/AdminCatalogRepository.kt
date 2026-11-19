package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dtos.VideoGameDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DuplicateGameException : IllegalArgumentException()

class AdminCatalogRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val access: AdminAccessRepository
) {
    suspend fun loadGames(): List<VideoGameDto> {
        access.requireAdmin()
        return db.collection("video-Games").get().await().documents.mapNotNull {
            it.toObject(VideoGameDto::class.java)?.copy(videoGameId = it.id)
        }.sortedBy { it.name.lowercase() }
    }

    suspend fun save(game: VideoGameDto): String {
        access.requireAdmin()
        val games = loadGames()
        if (games.any { it.videoGameId != game.videoGameId &&
                    it.name.trim().equals(game.name.trim(), ignoreCase = true) }) {
            throw DuplicateGameException()
        }
        val collection = db.collection("video-Games")
        val ref = if (game.videoGameId.isBlank()) collection.document() else collection.document(game.videoGameId)
        val fields = mapOf(
            "videoGameId" to ref.id, "name" to game.name, "description" to game.description,
            "developer" to game.developer, "platform" to game.platform,
            "releaseDate" to game.releaseDate, "imageUrl" to game.imageUrl,
            "genres" to game.genres, "archived" to game.archived
        )
        if (game.videoGameId.isBlank()) ref.set(fields).await() else ref.update(fields).await()
        return ref.id
    }

    suspend fun setArchived(id: String, archived: Boolean) {
        access.requireAdmin()
        db.collection("video-Games").document(id).update("archived", archived).await()
    }
}
