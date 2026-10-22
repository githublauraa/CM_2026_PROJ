package com.example.voxel_review.data.dataSource.impl

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.dataSource.services.ReviewRetrofitService
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    private val service: ReviewRetrofitService
): ReviewRemoteDataSource {


    override suspend fun createReview(userId: String, videoGameId: String, review: CreateReviewDto) {
	    return service.createReview(userId, videoGameId, review )
    }

    override suspend fun deleteReview(id: String): Unit {
	    service.deleteReview(id)
    }

    override suspend fun getUserReviews(id: String): List<ReviewDto> {
	    return service.getUserReviews(id)
    }

    override suspend fun getGameReviews(
        id: String,
        userId: String?
    ): List<ReviewDto> {
        return service.getGameReviews(id)
    }


    override suspend fun updateReview(id: String, review: CreateReviewDto) {
	    return service.updateReview(id, review)
    }

    override suspend fun getReviewComments(id: String): List<ReviewDto> {
        return service.getReviewComments(id)
    }

    override suspend fun sendOrDeleteReview(reviewId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun listenReview(gameId: String): Flow<List<ReviewDto>> {
        TODO("Not yet implemented")
    }

}
