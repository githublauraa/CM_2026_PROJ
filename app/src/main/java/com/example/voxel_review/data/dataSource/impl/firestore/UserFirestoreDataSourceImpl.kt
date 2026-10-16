package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserDtoGeneric
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import android.util.Log

class UserFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUserById(id: String): UserDtoGeneric {

        // Obtiene la referencia del documento del usuario en Firestore usando su ID
        val docRef = db.collection("users").document(id)

        // Consulta el documento y espera la respuesta de Firestore
        val respuesta = docRef.get().await()

        // Convierte el documento a UserProfileFirestoreDto y lo retorna
        // Si el usuario no existe, lanza una excepción
        return respuesta.toObject(UserProfileFirestoreDto::class.java)
            ?: throw Exception("No se encontró el usuario")
    }

    override suspend fun getAllUsers(): List<UserDtoGeneric> {
        val respuesta = db.collection("users")
            .get()
            .await()

        return respuesta.documents.mapNotNull { documento ->
            documento.toObject(UserProfileFirestoreDto::class.java)
        }
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        //crear el documento en la coleccion users con el id del usuario, la PK es el userId
        val docRef = db.collection("users").document(userId)
        //insertarlo el registerUserDto dentro del documento
        docRef.set(registerUserDto).await()
    }
}