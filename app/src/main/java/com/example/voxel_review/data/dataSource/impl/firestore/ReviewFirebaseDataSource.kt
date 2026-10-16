package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import jakarta.inject.Inject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ReviewFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) : ReviewRemoteDataSource {
    override suspend fun getUserReviews(id: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameReviews(id: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReview(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createReview(
        userId: String,
        videoGameId: String,
        review: CreateReviewDto
    ) {
        //crear la reseña con los ids
        val newReview = review.copy(
            userId = userId,
            videoGameId = videoGameId,

        )
        //guarda en la coleccion reviews la reseña
        db.collection("reviews").add(newReview).await()
    }

    override suspend fun updateReview(
        id: String,
        review: CreateReviewDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewComments(id: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }
}