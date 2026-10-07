package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.dataSource.impl.ReviewRetrofitDataSourceImpl
import com.example.voxel_review.data.dtos.toReviewInfo
import com.example.voxel_review.data.review.ReviewInfo
import retrofit2.HttpException
import javax.inject.Inject

class ReviewRepository
    @Inject
    constructor(
        private val reviewRemoteDataSource: ReviewRetrofitDataSourceImpl,
    ) {
        suspend fun getGameReviews(id: Int): Result<List<ReviewInfo>> =
            try {
                val reviews = reviewRemoteDataSource.getGameReviews(id)
                val reviewsInfo = reviews.map { it.toReviewInfo() }
                Result.success(reviewsInfo)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
