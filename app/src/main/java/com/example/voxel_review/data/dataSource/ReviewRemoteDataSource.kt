package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import com.example.voxel_review.data.review.ReviewInfo

interface ReviewRemoteDataSource {
	suspend fun getUserReviews(id: String): List<ReviewDto>
	suspend fun getGameReviews(id: String): List<ReviewDto>
	suspend fun deleteReview(id: String): Boolean
	suspend fun createReview(userId: String, videoGameId: String, review: CreateReviewDto): Unit
	suspend fun updateReview(id: String, review: CreateReviewDto): Unit
}
