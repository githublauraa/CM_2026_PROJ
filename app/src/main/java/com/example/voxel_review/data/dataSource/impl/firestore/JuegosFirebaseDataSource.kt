package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.VideoGameRemoteDataSource
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.example.voxel_review.data.dtos.VideoGameDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class JuegosFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
): VideoGameRemoteDataSource {
    override suspend fun getAllVideoGames(): List<VideoGameDto> {
        val respuesta = db.collection("video-Games").get().await()

        return respuesta.documents.mapNotNull { docs ->
            docs.toObject(VideoGameDto::class.java)
        }
    }

    override suspend fun getVideoGameById(id: String): VideoGameDto {
        // Obtiene la referencia del documento del juego en Firestore usando su ID
        val docRef = db.collection("video-Games").document(id)

        // Consulta el documento y espera la respuesta de Firestore
        val respuesta = docRef.get().await()

        // Convierte el documento a UserProfileFirestoreDto y lo retorna
        // Si el usuario no existe, lanza una excepción
        return respuesta.toObject(VideoGameDto::class.java)
            ?: throw Exception("No se encontró el usuario")
    }
}