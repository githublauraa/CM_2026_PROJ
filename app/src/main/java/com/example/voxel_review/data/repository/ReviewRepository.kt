package com.example.voxel_review.data.repository

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import com.example.voxel_review.data.dataSource.impl.firestore.ReviewFirebaseDataSourceImpl
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.toReviewInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
import retrofit2.HttpException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewFirebaseDataSourceImpl,
    private val authRepository: AuthRepository,
    private val strings: AppStrings
) {
    suspend fun getReceivedLikes(userId: String): Result<List<NotificationInfo>> = try {
        Result.success(reviewRemoteDataSource.getReceivedLikes(userId))
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(Exception(strings[R.string.error_received_likes], e))
    }

    suspend fun getAllReviews(): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getAllReviews()
            Result.success(reviews.map { it.toReviewInfo() })
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_reviews], e))
        }
    }

    suspend fun getRecentReviewsWithLocation(): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getRecentReviewsWithLocation()
            Result.success(reviews.map { it.toReviewInfo() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_map_reviews], e))
        }
    }

    fun getSelectedReviewLive(gameId: String, reviewId: String): Flow<ReviewInfo?> =
        reviewRemoteDataSource.listenSelectedReview(gameId, reviewId).map { reviews ->
            val selectedReview = if (reviewId.isBlank()) {
                reviews.firstOrNull()
            } else {
                reviews.find { review -> review.reviewId == reviewId }
            }
            selectedReview?.toReviewInfo()
        }.catch { error ->
            if (error is CancellationException || error !is Exception) throw error
            throw Exception(strings[R.string.error_review], error)
        }

    suspend fun getGameReviews(id: String): Result<List<ReviewInfo>> {
        return try {
            val currentUser = authRepository.currentUser?.uid ?:""
            val reviews = reviewRemoteDataSource.getGameReviews(id,currentUser)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_game_reviews], e))
        }
    }

    suspend fun getUserReviews(id: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getUserReviews(id)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_profile_reviews], e))
        }
    }

    suspend fun createReview(
        userId: String,
        videoGameId: String,
        review: CreateReviewDto
    ): Result<Unit> {
        return try {
            reviewRemoteDataSource.createReview(userId, videoGameId, review)
            Result.success(Unit)
        } catch (e: HttpException) {
            val message = if (e.code() == 400) {
                strings[R.string.error_review_duplicate]
            } else {
                strings[R.string.error_review_post]
            }
            Result.failure(Exception(message, e))
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_review_post], e))
        }
    }

    suspend fun deleteReview(reviewId: String): Result<Unit> {
        return try {
            reviewRemoteDataSource.deleteReview(reviewId)
            Result.success(Unit)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_review_delete], e))
        }
    }

    suspend fun updateReview(id: String, review: CreateReviewDto): Result<Unit> {
        return try {
            reviewRemoteDataSource.updateReview(id, review)
            Result.success(Unit)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_review_update], e))
        }
    }

    suspend fun getReviewComments(id: String): Result<List<ReviewInfo>> {
        return try {
            val comments = reviewRemoteDataSource.getReviewComments(id)
            val commentsInfo = comments.map { it.toReviewInfo() }
            Result.success(commentsInfo)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_comments], e))
        }
    }

    suspend fun toggleReviewReaction(reviewId: String, userId: String, dislike: Boolean): Result<Unit>{
        return try{
            reviewRemoteDataSource.sendOrDeleteReview(reviewId, userId, dislike)
            Result.success(Unit)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_review_reaction], e))
        }
    }

    suspend fun getReviewsLive(gameId: String): Flow<List<ReviewInfo>> {
        return reviewRemoteDataSource.listenReview(gameId)
            .map { reviews ->
                reviews.map { it.toReviewInfo() }
            }.catch { error ->
                if (error is CancellationException || error !is Exception) throw error
                throw Exception(strings[R.string.error_game_reviews], error)
            }
    }

    suspend fun getFollowedUsersReviews(currentUserId: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getFollowedUsersReviews(currentUserId)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Exception) {
            Result.failure(Exception(strings[R.string.error_followed_reviews], e))
        }
    }

}
