package com.example.voxel_review.data.dataSource.impl

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.dataSource.services.ReviewRetrofitService
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    val service: ReviewRetrofitService
): ReviewRemoteDataSource {
    override suspend fun getGameReviews(id: Int): List<ReviewDto> {
	    return service.getGameReviews(id)
    }

    override suspend fun createReview(userId: Int, videoGameId: Int, review: CreateReviewDto) {
	    return service.createReview(userId, videoGameId, review )
    }

    override suspend fun deleteReview(id: Int): Boolean {
	    return service.deleteReview(id)
    }

    override suspend fun getUserReviews(id: Int): List<ReviewDto> {
	    return service.getUserReviews(id)
    }

    override suspend fun updateReview(id: Int, review: CreateReviewDto) {
	    return service.updateReview(id, review)
    }
}
