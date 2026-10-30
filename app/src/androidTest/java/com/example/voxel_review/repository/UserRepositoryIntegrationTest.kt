package com.example.voxel_review.repository

import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserRepositoryIntegrationTest {


    //base de datos
    private val db = Firebase.firestore

    private val auth = Firebase.auth

    private lateinit var userRepository: UserRepository

    private fun generateUser(i: Int): UserProfileFirestoreDto = UserProfileFirestoreDto(
        userId = "user_$i",
        username = "username_$i",
        bio = "Bio de usuario $i",
        photoUrl = "https://picsum.photos/200/200?random=$i",
        numReviews = i * 2,
        likes = i * 5,
        avgRating = (i % 6).toFloat(),
        followed = false,
        followersCount = i * 10,
        followingCount = i * 5
    )


    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: IllegalStateException) {
            // La instancia ya fue configurada o utilizada.
        }

        val userDataSource = UserFirestoreDataSourceImpl(db)
        val authDataSource = AuthRemoteDataSource(auth)
        val authRepository = AuthRepository(authDataSource)

        userRepository = UserRepository(userDataSource, authRepository)

        val batch = db.batch()

        repeat(10) { i ->
            val user = generateUser(i)
            batch.set(
                db.collection("users").document(user.userId),
                user
            )
        }

        batch.commit().await()
    }


    //dto -> mapper -> ui
    //result
    //try catch

    @Test
    fun getUserById_validId_correctUser() = runTest {
        //arr
        val id = "user_9"
        val expectedName = "username_9"
        //act
        val result = userRepository.getUserById(id)
        //assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result?.getOrNull()?.nick).isEqualTo(expectedName)
    }

    @Test
    fun getUserById_invalidId_returnFailure() = runTest {
        //arr
        val id = "user_999"
        //act
        val result = userRepository.getUserById(id)
        //assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("User not found")
    }

    //verificar que traiga los 10 usuarios
    @Test
    fun getAllUsers_existingUsers_returnAllUses() = runTest {
        //arr
        val cantidadUsuarios = 10
        //la bd ya creo los usuarios

        //act
        val users = userRepository.getAllUsers().getOrNull()
        //assert
        Truth.assertThat(users).hasSize(cantidadUsuarios)
    }

    //registrar un usuario
    @Test
    fun registerUser_validUser_createdUser() = runTest {
        //arr
        val userId = "user_0"
        val username = "prueba"
        val bio = "bio_prueba"
        val photo = "https://picsum.photos/200/200?random=1"
        //act
        val result = userRepository.registerUser(username,bio,userId,photo)
        //assert
        Truth.assertThat(result.isSuccess).isTrue()

        val user = userRepository.getUserById(userId).getOrThrow()
        Truth.assertThat(user.nick).isEqualTo(username)
    }



    //codigo que quiero que se ejecute despues de terminar las pruebas
    @After
    fun tearDown() = runTest {

        //borrar usuarios
        val users = db.collection("users").get().await()
        users.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }

        //borrar subcolecciones
        for (userDoc in users) {
            val followers = userDoc.reference.collection("followers").get().await()
            for (follower in followers){
                follower.reference.delete().await()
            }
            val following = userDoc.reference.collection("following").get().await()
            for (f in following) {
                f.reference.delete().await()
            }
        }
    }


}