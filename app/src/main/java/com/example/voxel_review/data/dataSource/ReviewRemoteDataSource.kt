package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import com.example.voxel_review.data.review.ReviewInfo

interface ReviewRemoteDataSource {
	suspend fun getUserReviews(id: Int): List<ReviewDto>
	suspend fun getGameReviews(id: Int): List<ReviewDto>
	suspend fun deleteReview(id: Int): Boolean
	suspend fun createReview(userId: Int, videoGameId: Int, review: CreateReviewDto): Unit
	suspend fun updateReview(id: Int, review: CreateReviewDto): Unit
}
