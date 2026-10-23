package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dataSource.impl.firestore.ReviewFirebaseDataSourceImpl
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.toReviewInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import retrofit2.HttpException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewFirebaseDataSourceImpl,
    private val authRepository: AuthRepository
    ) {
    suspend fun getGameReviews(id: String): Result<List<ReviewInfo>> {
        return try {
            val currentUser = authRepository.currentUser?.uid ?:""
            val reviews = reviewRemoteDataSource.getGameReviews(id,currentUser)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getUserReviews(id: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getUserReviews(id)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
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
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteReview(reviewId: String): Result<Unit> {
        return try {
            reviewRemoteDataSource.deleteReview(reviewId)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateReview(id: String, review: CreateReviewDto): Result<Unit> {
        return try {
            reviewRemoteDataSource.updateReview(id, review)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewComments(id: String): Result<List<ReviewInfo>> {
        return try {
            val comments = reviewRemoteDataSource.getReviewComments(id)
            val commentsInfo = comments.map { it.toReviewInfo() }
            Result.success(commentsInfo)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOrDeleteReviewLike(reviewId: String, userId: String): Result<Unit>{
        return try{
            reviewRemoteDataSource.sendOrDeleteReview(reviewId, userId)
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getReviewsLive(gameId: String): Flow<List<ReviewInfo>> {
        return reviewRemoteDataSource.listenReview(gameId)
            .map { reviews ->
                reviews.map { it.toReviewInfo() }
            }
    }

    suspend fun getFollowedUsersReviews(currentUserId: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getFollowedUsersReviews(currentUserId)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}
