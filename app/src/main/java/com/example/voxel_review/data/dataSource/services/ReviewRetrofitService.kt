package com.example.voxel_review.data.dataSource.services

import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewRetrofitService {
	@GET("reviews/video-game/{id}")
	suspend fun getGameReviews(@Path("id") id: String): List<ReviewDto>

	@GET("reviews/user/{id}")
	suspend fun getUserReviews(@Path("id") id: String): List<ReviewDto>

	@POST("reviews/user/{userId}/video-game/{video-gameId}")
	suspend fun createReview(@Path("userId") userId: String, @Path("video-gameId") videoGameId: String, @Body review: CreateReviewDto): Unit

	@DELETE("reviews/{id}")
	suspend fun deleteReview(@Path("id") id: String) : Boolean

	@PUT("reviews/{id}")
	suspend fun updateReview(@Path("id") id: String, @Body review: CreateReviewDto) : Unit
}
	
