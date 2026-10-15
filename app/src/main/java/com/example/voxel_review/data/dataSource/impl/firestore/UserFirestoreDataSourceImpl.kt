package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserProfileDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class UserFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore) : UserRemoteDataSource {
    override suspend fun getUserById(id: String): UserProfileDto {
        //obtener la referencia al documento con ese id
        val docRef = db.collection("users").document(id)
        //obtener el documento hacia el front
        val respuesta = docRef.get().await()
        //convertir el documento a UserProfileDto
        return respuesta.toObject(UserProfileDto::class.java)?: throw Exception ("No se encontro el usuario")
    }

    override suspend fun getAllUsers(): List<UserProfileDto> {
        return emptyList()
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        //crear el documento en la coleccion users con el id del usuario, la PK es el userId
        val docRef = db.collection("users").document(userId)
        //insertarlo el registerUserDto dentro del documento
        docRef.set(registerUserDto).await()
    }
}