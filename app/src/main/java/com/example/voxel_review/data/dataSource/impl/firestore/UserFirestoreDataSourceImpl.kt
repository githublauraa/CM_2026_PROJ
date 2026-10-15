package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserDtoGeneric
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import android.util.Log

class UserFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore) : UserRemoteDataSource {
    override suspend fun getUserById(id: String): UserDtoGeneric {
        Log.d("FIRESTORE_TEST", "Buscando usuario: $id")

        val respuesta = db.collection("users")
            .document(id)
            .get()
            .await()

        Log.d("FIRESTORE_TEST", "Existe: ${respuesta.exists()}")
        Log.d("FIRESTORE_TEST", "Datos: ${respuesta.data}")

        val userDto = respuesta.toObject(
            UserProfileFirestoreDto::class.java
        ) ?: throw Exception("No se encontró el usuario")

        return userDto.copy(userId = respuesta.id)
    }

    override suspend fun getAllUsers(): List<UserDtoGeneric> {
        return emptyList()
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        //crear el documento en la coleccion users con el id del usuario, la PK es el userId
        val docRef = db.collection("users").document(userId)
        //insertarlo el registerUserDto dentro del documento
        docRef.set(registerUserDto).await()
    }
}