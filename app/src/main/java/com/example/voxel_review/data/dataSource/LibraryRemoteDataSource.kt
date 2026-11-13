package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.LibraryEntryDto

/**
 * Contrato remoto para gestionar la colección personal de videojuegos de un usuario.
 */
interface LibraryRemoteDataSource {
    suspend fun getUserLibrary(userId: String): List<LibraryEntryDto>
    suspend fun getGameEntry(userId: String, videoGameId: String): LibraryEntryDto?
    suspend fun upsertLibraryEntry(userId: String, entry: LibraryEntryDto)
    suspend fun deleteLibraryEntry(userId: String, videoGameId: String)
}
