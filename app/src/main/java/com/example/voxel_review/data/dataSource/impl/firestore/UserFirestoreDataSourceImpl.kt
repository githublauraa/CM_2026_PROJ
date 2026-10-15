package com.example.voxel_review.data.dataSource.impl.firestore

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserProfileDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class UserFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore) : UserRemoteDataSource {
    override suspend fun getUserById(id: String): UserProfileDto {
        TODO("Not yet implemented")

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