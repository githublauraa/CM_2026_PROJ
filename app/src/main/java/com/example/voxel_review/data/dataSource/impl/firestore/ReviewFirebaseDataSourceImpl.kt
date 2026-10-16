package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.ReviewDto
import jakarta.inject.Inject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.example.voxel_review.data.dtos.VideoGameDto
import com.example.voxel_review.data.dtos.CreateReviewUserDto

class ReviewFirebaseDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ReviewRemoteDataSource {
    //obtener reseñas dado un usuario
    override suspend fun getUserReviews(id: String): List<ReviewDto> {
        val respuesta = db.collection("reviews")
            .whereEqualTo("userdId", id).get().await()
        return respuesta.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.copy(reviewId = doc.id)?: throw Exception("Reseña no encontrada")
        }
    }

    //dado un juego obtener las reseñas
    override suspend fun getGameReviews(id: String): List<ReviewDto> {
        val respuesta = db.collection("reviews")
            .whereEqualTo("videoGameId", id).get().await()
        return respuesta.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.copy(reviewId = doc.id)?: throw Exception("Reseña no encontrada")
        }
    }

    override suspend fun deleteReview(id: String) {
        db.collection("reviews").document(id).delete().await()
    }

    override suspend fun createReview(
        userId: String,
        videoGameId: String,
        review: CreateReviewDto
    ) {
        // Obtener usuario
        val userDocument = db.collection("users")
            .document(userId)
            .get()
            .await()

        if (!userDocument.exists()) {
            throw Exception("Usuario no encontrado")
        }

        // Obtener videojuego
        val gameDocument = db.collection("video-Games")
            .document(videoGameId)
            .get()
            .await()

        if (!gameDocument.exists()) {
            throw Exception("Videojuego no encontrado")
        }

        // Convertir los datos
        val user = userDocument.toObject(
            UserProfileFirestoreDto::class.java
        ) ?: throw Exception("Error al obtener usuario")

        val videoGame = gameDocument.toObject(
            VideoGameDto::class.java
        ) ?: throw Exception("Error al obtener videojuego")

        // Crear reseña con datos desnormalizados
        val newReview = review.copy(
            userId = userId,
            videoGameId = videoGameId,

            user = CreateReviewUserDto(
                username = user.username,
                photoUrl = user.photoUrl
            ),

            videoGame = videoGame.copy(
                videoGameId = gameDocument.id
            )
        )

        // Guardar reseña
        db.collection("reviews")
            .add(newReview)
            .await()
    }

    override suspend fun updateReview(
        id: String,
        review: CreateReviewDto
    ) {
        db.collection("reviews").document(id).set(review).await()
    }

    //obtener los comentarios del juego
    override suspend fun getReviewComments(id: String): List<ReviewDto> {

        val respuesta = db.collection("reviews").whereEqualTo("videoGameId", id).get().await()

        return respuesta.documents.mapNotNull {doc ->
            doc.toObject(ReviewDto::class.java)
                ?.copy(reviewId = doc.id)
        }

    }
}