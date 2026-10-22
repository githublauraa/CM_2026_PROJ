package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import kotlinx.coroutines.flow.Flow

interface ReviewRemoteDataSource {
	suspend fun getUserReviews(id: String): List<ReviewDto>
	suspend fun getGameReviews(id: String, userId: String? = ""): List<ReviewDto>
	suspend fun deleteReview(id: String): Unit
	suspend fun createReview(userId: String, videoGameId: String, review: CreateReviewDto): Unit
	suspend fun updateReview(id: String, review: CreateReviewDto): Unit
	suspend fun getReviewComments(id: String): List<ReviewDto>

	suspend fun  sendOrDeleteReview(reviewId: String, userId: String): Unit


	suspend fun listenReview(gameId: String): Flow<List<ReviewDto>>
}
