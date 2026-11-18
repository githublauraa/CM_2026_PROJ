package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.ReviewRemoteDataSource
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
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
import java.time.Instant
import java.time.temporal.ChronoUnit

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
            existingReview.copy(
                reviewId = doc.id,
                videoGame = videoGame
            ).withCurrentUserReactions(currentUserId)
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

            val userDocument = db.collection("users")
                .document(review.userId)
                .get()
                .await()

            val user = userDocument.toObject(
                UserProfileFirestoreDto::class.java
            )

            review.copy(
                reviewId = doc.id,
                user = com.example.voxel_review.data.dtos.UserDto(
                    username = user?.username ?: "",
                    photoUrl = user?.photoUrl
                )
            ).withCurrentUserReactions(userId)
        }
    }

    // El listado solo necesita las puntuaciones; no consulta usuarios ni likes.
    suspend fun getAllReviews(): List<ReviewDto> {
        return db.collection("reviews").get().await().documents.mapNotNull { doc ->
            doc.toObject(ReviewDto::class.java)?.copy(reviewId = doc.id)
        }
    }

    suspend fun getRecentReviewsWithLocation(): List<ReviewDto> {
        val now = Instant.now()
        val cutoff = now.minus(24, ChronoUnit.HOURS)

        // Los documentos guardan ISO-8601 con fracciones de segundo opcionales.
        val firstSecond = cutoff.truncatedTo(ChronoUnit.SECONDS).toString().removeSuffix("Z")
        val nextSecond = now.truncatedTo(ChronoUnit.SECONDS).plusSeconds(1).toString().removeSuffix("Z")
        val snapshot = db.collection("reviews")
            .whereGreaterThanOrEqualTo("createdAt", firstSecond)
            .whereLessThan("createdAt", nextSecond)
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val review = document.toObject(ReviewDto::class.java) ?: return@mapNotNull null
            if (review.latitude == null || review.longitude == null) return@mapNotNull null

            val publishedAt = runCatching { Instant.parse(review.createdAt) }.getOrNull()
            if (publishedAt == null || publishedAt !in cutoff..now) return@mapNotNull null

            review.copy(reviewId = document.id)
        }
    }

    // El detalle escucha únicamente la reseña visible.
    fun listenSelectedReview(gameId: String, reviewId: String): Flow<List<ReviewDto>> = callbackFlow {
        val listener = if (reviewId.isNotBlank()) {
            db.collection("reviews").document(reviewId).addSnapshotListener { doc, error ->
                if (error != null) {
                    close(error)
                } else {
                    val review = doc?.toObject(ReviewDto::class.java)?.copy(reviewId = doc.id)
                    trySend(listOfNotNull(review))
                }
            }
        } else {
            db.collection("reviews").whereEqualTo("videoGameId", gameId).limit(1)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                    } else {
                        trySend(snapshot?.documents.orEmpty().mapNotNull { doc ->
                            doc.toObject(ReviewDto::class.java)?.copy(reviewId = doc.id)
                        })
                    }
                }
        }
        awaitClose { listener.remove() }
    }.map { reviews ->
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        reviews.map { review ->
            val game = review.videoGame ?: db.collection("video-Games")
                .document(review.videoGameId).get().await().let { doc ->
                    doc.toObject(VideoGameDto::class.java)?.copy(videoGameId = doc.id)
                }
            review.copy(videoGame = game).withCurrentUserReactions(currentUserId)
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
            createdAt = Instant.now().toString(),
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

    suspend fun getReceivedLikes(userId: String): List<NotificationInfo> {
        val reviews = db.collection("reviews").whereEqualTo("userId", userId).get().await()
        val actors = mutableMapOf<String, UserProfileFirestoreDto?>()

        return reviews.documents.flatMap { review ->
            val likes = review.reference.collection("likes").get().await()
            val gameName = review.getString("videoGame.name").orEmpty()
            val message = if (gameName.isBlank()) "Le dio me gusta a tu reseña"
                else "Le dio me gusta a tu reseña de $gameName"

            likes.documents.mapNotNull { like ->
                if (like.id == userId) return@mapNotNull null
                val actor = actors.getOrPut(like.id) {
                    db.collection("users").document(like.id).get().await()
                        .toObject(UserProfileFirestoreDto::class.java)
                } ?: return@mapNotNull null

                like.getTimestamp("timestamp") to NotificationInfo(
                    id = like.reference.path,
                    title = actor.username,
                    message = message,
                    imageUrl = actor.photoUrl.orEmpty(),
                    reviewId = review.id,
                    gameId = review.getString("videoGameId").orEmpty(),
                    reviewAuthorId = userId
                )
            }
        }.sortedByDescending { it.first }.map { it.second }
    }

    override suspend fun sendOrDeleteReview(reviewId: String, userId: String, dislike: Boolean) {
        require(reviewId.isNotBlank()) { "El ID de la reseña es obligatorio" }
        require(userId.isNotBlank()) { "Debes iniciar sesión para reaccionar a la reseña" }

        val reviewRef = db.collection("reviews").document(reviewId)
        val likesRef = reviewRef.collection("likes").document(userId)
        val dislikesRef = reviewRef.collection("dislikes").document(userId)
        val selectedRef = if (dislike) dislikesRef else likesRef
        val selectedCounter = if (dislike) "cantidadDislikes" else "cantidadLikes"
        val oppositeRef = if (dislike) likesRef else dislikesRef
        val oppositeCounter = if (dislike) "cantidadLikes" else "cantidadDislikes"

        db.runTransaction { transaction ->
            val selected = transaction.get(selectedRef).exists()
            val opposite = transaction.get(oppositeRef).exists()

            if (selected) {
                transaction.delete(selectedRef)
                transaction.update(reviewRef, selectedCounter, FieldValue.increment(-1))
            } else {
                if (opposite) {
                    transaction.delete(oppositeRef)
                    transaction.update(reviewRef, oppositeCounter, FieldValue.increment(-1))
                }
                transaction.set(selectedRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(reviewRef, selectedCounter, FieldValue.increment(1))
            }
        }.await()
    }

    private suspend fun ReviewDto.withCurrentUserReactions(userId: String?): ReviewDto {
        if (userId.isNullOrBlank()) return this

        val reviewRef = db.collection("reviews").document(reviewId)
        return copy(
            likedByCurrentUser = reviewRef.collection("likes").document(userId).get().await().exists(),
            dislikedByCurrentUser = reviewRef.collection("dislikes").document(userId).get().await().exists()
        )
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
        reviews.map { it.withCurrentUserReactions(currentUserId) }
    }

    override suspend fun getFollowedUsersReviews(currentUserId: String): List<ReviewDto> {
        if (currentUserId.isBlank()) return emptyList()

        // 1. Obtener la lista de IDs de usuarios seguidos (users/{currentUserId}/following)
        val followingSnapshot = db.collection("users")
            .document(currentUserId)
            .collection("following")
            .get()
            .await()

        val followedUserIds = followingSnapshot.documents.map { it.id }

        if (followedUserIds.isEmpty()) return emptyList()

        // 2. Traer las reseñas donde userId está en la lista de seguidos (whereIn limita a 30)
        val reviewsSnapshot = db.collection("reviews")
            .whereIn("userId", followedUserIds.take(30))
            .get()
            .await()

        val gamesById = mutableMapOf<String, VideoGameDto?>()
        return reviewsSnapshot.documents.mapNotNull { doc ->
            val review = doc.toObject(ReviewDto::class.java) ?: return@mapNotNull null

            val videoGame = review.videoGame?.takeIf { it.name.isNotBlank() }
                ?: if (review.videoGameId.isNotBlank()) {
                    if (!gamesById.containsKey(review.videoGameId)) {
                        val gameDocument = db.collection("video-Games")
                            .document(review.videoGameId).get().await()
                        gamesById[review.videoGameId] = gameDocument
                            .toObject(VideoGameDto::class.java)
                            ?.copy(videoGameId = gameDocument.id)
                    }
                    gamesById[review.videoGameId]
                } else null

            review.copy(
                reviewId = doc.id,
                videoGame = videoGame
            ).withCurrentUserReactions(currentUserId)
        }
    }
}
