package com.example.voxel_review.data.dataSource.impl.firestore

import android.R
import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserDtoGeneric
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.google.firebase.firestore.FieldValue
import java.nio.file.Files.copy
class UserFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUserById(id: String, currentUserId: String): UserProfileFirestoreDto? {

        // Obtiene la referencia del documento del usuario en Firestore usando su ID
        val docRef = db.collection("users").document(id)

        // Consulta el documento y espera la respuesta de Firestore
        val respuesta = docRef.get().await()

        // Convierte el documento a UserProfileFirestoreDto y lo retorna
        // Si el usuario no existe, lanza una excepción
        val user = respuesta.toObject(UserProfileFirestoreDto::class.java)
            ?.copy(userId = respuesta.id)
            ?: return null

        val followerDoc = db.collection("users").document(id).collection("followers").document(currentUserId).get().await()

        val exist =  followerDoc.exists()

        user.followed = exist

        return user
    }

    override suspend fun getAllUsers(): List<UserProfileFirestoreDto> {
        val usersSnapshot = db.collection("users").get().await()
        val reviewsSnapshot = db.collection("reviews").get().await()

        val reviewsPorUsuario = reviewsSnapshot.documents
            .mapNotNull { it.getString("userId") }
            .groupingBy { it }
            .eachCount()

        return usersSnapshot.documents.mapNotNull { documento ->
            documento.toObject(UserProfileFirestoreDto::class.java)
                ?.copy(
                    userId = documento.id,
                    numReviews = reviewsPorUsuario[documento.id] ?: 0
                )
        }
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        //crear el documento en la coleccion users con el id del usuario, la PK es el userId
        val docRef = db.collection("users").document(userId)
        //insertarlo el registerUserDto dentro del documento
        docRef.set(registerUserDto).await()
    }

    override suspend fun updateUserBio(
        userId: String,
        bio: String
    ) {
        db.collection("users")
            .document(userId)
            .update("bio", bio)
            .await()
    }

    override suspend fun updateUsername(userdId: String, username: String) {
        db.collection("users")
            .document(userdId)
            .update("username", username)
            .await()
    }

    override suspend fun followOrUnfollow(
        currentUserId: String,
        targetUserId: String
    ) {
        val currentUserRef = db.collection("users").document(currentUserId)
        val targetUserRef = db.collection("users").document(targetUserId)

        val followingRef = currentUserRef.collection("following").document(targetUserId)
        val followersRef = targetUserRef.collection("followers").document(currentUserId)

        db.runTransaction { transaction ->

            val followingDoc = transaction.get(followingRef)

            if (followingDoc.exists()){
                transaction.delete(followingRef)
                transaction.delete(followersRef)
                transaction.update(currentUserRef, "followingCount", FieldValue.increment(-1))
                transaction.update(targetUserRef, "followersCount", FieldValue.increment(-1))
            } else {
                transaction.set(followingRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.set(followersRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(currentUserRef, "followingCount", FieldValue.increment(1))
                transaction.update(targetUserRef, "followersCount", FieldValue.increment(1))
            }
        }.await()
    }
}