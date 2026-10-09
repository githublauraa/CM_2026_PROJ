package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.dataSource.impl.ReviewRetrofitDataSourceImpl
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.toReviewInfo
import com.example.voxel_review.data.review.ReviewInfo
import retrofit2.HttpException
import javax.inject.Inject

class ReviewRepository @Inject constructor(
        private val reviewRemoteDataSource: ReviewRetrofitDataSourceImpl,
    ) {
        suspend fun getGameReviews(id: String): Result<List<ReviewInfo>> {
            return try {
                val reviews = reviewRemoteDataSource.getGameReviews(id)
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

    suspend fun createReview(userId: String, videoGameId: String, review: CreateReviewDto):Result<Unit> {
        return try{
            reviewRemoteDataSource.createReview(userId, videoGameId, review)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    }
