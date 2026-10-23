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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.channels.awaitClose

class ReviewFirebaseDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ReviewRemoteDataSource {
    //obtener reseñas dado un usuario
    override suspend fun getUserReviews(id: String): List<ReviewDto> {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val respuesta = db.collection("reviews")
            .whereEqualTo("userId", id).get().await()
        return respuesta.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            val existingReview = review ?: throw Exception("Reseña no encontrada")
            // Las ediciones anteriores podían borrar los datos desnormalizados del juego.
            val videoGame = existingReview.videoGame
                ?: db.collection("video-Games")
                    .document(existingReview.videoGameId)
                    .get()
                    .await()
                    .let { gameDocument ->
                        gameDocument.toObject(VideoGameDto::class.java)
                            ?.copy(videoGameId = gameDocument.id)
                    }
            val hasLiked = if (!currentUserId.isNullOrBlank()) {
                doc.reference.collection("likes")
                    .document(currentUserId)
                    .get()
                    .await()
                    .exists()
            } else {
                false
            }

            existingReview.copy(
                reviewId = doc.id,
                videoGame = videoGame,
                likedByCurrentUser = hasLiked
            )
        }
    }

    //dado un juego obtener las reseñas
    override suspend fun getGameReviews(
        id: String,
        userId: String?
    ): List<ReviewDto> {

        val respuesta = db.collection("reviews")
            .whereEqualTo("videoGameId", id)
            .get()
            .await()

        return respuesta.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
                ?: throw Exception("Reseña no encontrada")

            var hasLiked = false

            if (!userId.isNullOrBlank()) {
                val likeSnapshot = doc.reference
                    .collection("likes")
                    .document(userId)
                    .get()
                    .await()

                hasLiked = likeSnapshot.exists()
            }

            val userDocument = db.collection("users")
                .document(review.userId)
                .get()
                .await()

            val user = userDocument.toObject(
                UserProfileFirestoreDto::class.java
            )

            review.copy(
                reviewId = doc.id,
                likedByCurrentUser = hasLiked,
                user = com.example.voxel_review.data.dtos.UserDto(
                    username = user?.username ?: "",
                    photoUrl = user?.photoUrl
                )
            )
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
        // Actualizar solo los campos editables conserva el juego, el usuario y sus imágenes.
        db.collection("reviews").document(id).update(
            mapOf(
                "gameplayRating" to review.gameplayRating,
                "graphicsRating" to review.graphicsRating,
                "storyRating" to review.storyRating,
                "content" to review.content
            )
        ).await()
    }

    //obtener los comentarios del juego
    override suspend fun getReviewComments(id: String): List<ReviewDto> {

        val respuesta = db.collection("reviews").whereEqualTo("videoGameId", id).get().await()

        return respuesta.documents.mapNotNull {doc ->
            doc.toObject(ReviewDto::class.java)
                ?.copy(reviewId = doc.id)
        }

    }

    override suspend fun sendOrDeleteReview(reviewId: String, userId: String) {
        require(reviewId.isNotBlank()) { "El ID de la reseña es obligatorio" }
        require(userId.isNotBlank()) { "Debes iniciar sesión para dar like" }

        val reviewRef = db.collection("reviews").document(reviewId)
        val likesRef = reviewRef.collection("likes").document(userId)

        db.runTransaction { transaction ->

            val likeDoc = transaction.get(likesRef)

            if (likeDoc.exists()){
                transaction.delete(likesRef)
                transaction.update(reviewRef, "cantidadLikes", FieldValue.increment(-1))
            } else {
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(reviewRef, "cantidadLikes", FieldValue.increment(1))
            }

        }.await()

    }

    override suspend fun listenReview(
        gameId: String
    ): Flow<List<ReviewDto>> = callbackFlow {
        val listener = db.collection("reviews")
            .whereEqualTo("videoGameId", gameId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val reviews = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ReviewDto::class.java)
                            ?.copy(reviewId = doc.id)
                    }

                    trySend(reviews)
                }
            }

        awaitClose { listener.remove() }
    }.map { reviews ->
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        reviews.map { review ->
            val hasLiked = if (currentUserId.isNullOrBlank()) {
                false
            } else {
                db.collection("reviews")
                    .document(review.reviewId)
                    .collection("likes")
                    .document(currentUserId)
                    .get()
                    .await()
                    .exists()
            }
            review.copy(likedByCurrentUser = hasLiked)
        }
    }
}
