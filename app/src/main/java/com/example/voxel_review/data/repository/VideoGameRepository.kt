package com.example.voxel_review.data.repository

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import com.example.voxel_review.data.dataSource.impl.firestore.JuegosFirebaseDataSourceImpl
import com.example.voxel_review.data.dtos.VideoGameDto
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

class VideoGameRepository @Inject constructor(
    private val videoGameRemoteDataSource: JuegosFirebaseDataSourceImpl,
    private val strings: AppStrings
) {

    suspend fun getAllVideoGames(): Result<List<VideoGameDto>> {
        return try {
            val videoGames = videoGameRemoteDataSource.getAllVideoGames()
            Result.success(videoGames)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_games], e))
        }
    }

    suspend fun getVideoGameById(id: String): Result<VideoGameDto> {
        return try {
            val videoGame = videoGameRemoteDataSource.getVideoGameById(id)
            Result.success(videoGame)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_game], e))
        }
    }
}
