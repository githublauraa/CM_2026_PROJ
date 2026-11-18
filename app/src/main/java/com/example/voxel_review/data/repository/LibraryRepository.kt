package com.example.voxel_review.data.repository

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import com.example.voxel_review.data.dataSource.impl.firestore.LibraryFirestoreDataSourceImpl
import com.example.voxel_review.data.dtos.LibraryEntryDto
import com.example.voxel_review.data.dtos.toLibraryGameInfo
import com.example.voxel_review.data.info.InfoGame.GameDetailInfo
import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/**
 * Repositorio encargado de administrar la colección personal / backlog de videojuegos
 * de cada usuario en Cloud Firestore.
 */
class LibraryRepository @Inject constructor(
    private val libraryRemoteDataSource: LibraryFirestoreDataSourceImpl,
    private val strings: AppStrings
) {

    suspend fun getUserLibrary(userId: String): Result<List<LibraryGameInfo>> {
        if (userId.isBlank()) return Result.success(emptyList())
        return try {
            val entries = libraryRemoteDataSource.getUserLibrary(userId)
                .mapNotNull { it.toLibraryGameInfo() }
            Result.success(entries)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_collection], e))
        }
    }

    suspend fun getGameStatus(userId: String, videoGameId: String): Result<LibraryStatus?> {
        if (userId.isBlank() || videoGameId.isBlank()) return Result.success(null)
        return try {
            val entry = libraryRemoteDataSource.getGameEntry(userId, videoGameId)
            Result.success(LibraryStatus.fromNameOrNull(entry?.status))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_library_status], e))
        }
    }

    suspend fun setGameStatus(
        userId: String,
        game: GameDetailInfo,
        status: LibraryStatus
    ): Result<Unit> {
        if (userId.isBlank() || game.id.isBlank()) {
            return Result.failure(IllegalArgumentException(strings[R.string.invalid_user_or_game]))
        }
        return try {
            val dto = LibraryEntryDto(
                videoGameId = game.id,
                gameName = game.nombre,
                gameImageUrl = game.imagen.ifBlank { game.banner }.ifBlank { null },
                gameDeveloper = game.desarrollador,
                status = status.name,
                updatedAt = System.currentTimeMillis()
            )
            libraryRemoteDataSource.upsertLibraryEntry(userId, dto)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_library_save], e))
        }
    }

    suspend fun removeGameFromLibrary(userId: String, videoGameId: String): Result<Unit> {
        if (userId.isBlank() || videoGameId.isBlank()) {
            return Result.failure(IllegalArgumentException(strings[R.string.invalid_user_or_game]))
        }
        return try {
            libraryRemoteDataSource.deleteLibraryEntry(userId, videoGameId)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_library_remove], e))
        }
    }
}
