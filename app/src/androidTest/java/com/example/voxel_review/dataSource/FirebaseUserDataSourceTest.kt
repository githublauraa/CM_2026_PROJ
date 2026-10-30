package com.example.voxel_review.dataSource

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import kotlinx.coroutines.tasks.await
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before

class FirebaseUserDataSourceTest {

    //base de datos
    private val db = Firebase.firestore

    //lateinit, para que no se inicialice al inicio, uno mismo lo inicializa
    private lateinit var dataSource: UserFirestoreDataSourceImpl


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


    //codigo que se ejecute antes de cada prueba
    @Before
    fun setUp() = runTest{
        try {
            //usar el emulador
            db.useEmulator("10.0.2.2",8080)
        } catch (e : Exception){}


        dataSource = UserFirestoreDataSourceImpl(db)

        //las pruebas deben ser replicables  (hacer click 30 veces y deberia dar el mismo resultado)
        //autonomas (no deben depender del resultado de otras pruebas)

        //insertar usuarios


        val batch = db.batch()
        repeat(10){i ->
            val user = generateUser(i)
            batch.set(db.collection("users").document(user.userId), user)

        }
        batch.commit().await()
    }


    //primero el nombre de lo que se prueba, segundo las precondiciones (usuarios existente), tercero lo que espero que me responda(usuario correcto)
    @Test
    fun getUserById_validId_correctUser() = runTest {
        //diseñar pruebas AAA
        //Arrange (preparar)
        val id = "user_0"
        val expectedName = "username_0"

        //Act (ejecutar) (realizar prueba)
        val result = dataSource.getUserById(id)
        //Assert (validar)
        //resultado no puede ser nulo porque se esta buscando algo
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.username).isEqualTo(expectedName)
        Truth.assertThat(result?.userId).isEqualTo(id)
        Truth.assertThat(result?.followed).isFalse()

    }


    //primero el nombre de lo que se prueba, segundo las precondiciones (usuarios existente), tercero lo que espero que me responda(usuario correcto)
    @Test
    fun getUserById_invalidId_null() = runTest {
        //diseñar pruebas AAA
        //Arrange (preparar)
        val id = "user_9999990"
        //Act (ejecutar) (realizar prueba)
        val result = dataSource.getUserById(id)
        //Assert (validar)
        Truth.assertThat(result).isNull()
    }


    @Test
    fun registerUser_insertDoc_DoccumentExist() = runTest {
        //AAA
        //Arrange (preparar)
        val user = RegisterUserDto(
            username = "username_1",
            bio = "Bio de usuario 1",
            photoUrl = "https://picsum.photos/200/200?random=1",
            FCMToken = ""
        )
        val userId = "999"
        //Act (ejecutar) (realizar prueba)
        dataSource.registerUser(user, userId)
        //Assert (validar)
        val result = dataSource.getUserById(userId)
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.username).isEqualTo(user.username)
        Truth.assertThat(result?.photoUrl).isEqualTo(user.photoUrl)
        Truth.assertThat(result?.bio).isEqualTo(user.bio)

    }

    //probar follow or unfollow
    @Test
    fun followOrUnfollowUser_followUser_UserFollow() = runTest {
        //arrange
        val currentUser = generateUser(1)
        val targetUser = generateUser(2)
        //act
        dataSource.followOrUnfollow(currentUser.userId, targetUser.userId)
        //assert
        val targetUserResult = dataSource.getUserById(targetUser.userId, currentUser.userId)
        Truth.assertThat(targetUserResult?.followed).isTrue()
    }

    //probar follow or unfollow (count)
    @Test
    fun followOrUnfollowUser_followUser_FollowerCountIncreament() = runTest {
        //arrange
        val currentUser = generateUser(1)
        val targetUser = generateUser(2)

        val oldData = dataSource.getUserById(targetUser.userId)
        //act
        dataSource.followOrUnfollow(currentUser.userId, targetUser.userId)
        //assert
        val targetUserResult = dataSource.getUserById(targetUser.userId)
        Truth.assertThat(targetUserResult?.followersCount).isGreaterThan(oldData?.followersCount)
    }

    //probar follow or unfollow (seguir y dejar de seguir)
    @Test
    fun followOrUnfollowUser_unfollow_followedFalse() = runTest {
        //arrange
        val currentUser = generateUser(1)
        val targetUser = generateUser(2)
        //act
        //seguir
        dataSource.followOrUnfollow(currentUser.userId, targetUser.userId)
        //dejar de seguir
        dataSource.followOrUnfollow(currentUser.userId, targetUser.userId)
        //assert
        val targetUserResult = dataSource.getUserById(targetUser.userId)
        Truth.assertThat(targetUserResult?.followed).isFalse()
    }

    //verificar cambio nombre usuario, usuario existente
    @Test
    fun updateUsername_validUser_corretUsername() = runTest {
        //arrange
        val user = generateUser(1)
        val newUsername = "prueba"
        //act
        dataSource.updateUsername(user.userId, newUsername)

        //assert
        val userResult = dataSource.getUserById(user.userId)
        Truth.assertThat(userResult?.username).isEqualTo(newUsername)
    }


    @Test
    fun updateBio_validUser_correctBio() = runTest {
        //arrange
        val user = generateUser(1)
        val newBio = "prueba"
        //act
        dataSource.updateUserBio(user.userId, newBio)
        //assert
        val resultado = dataSource.getUserById(user.userId)
        Truth.assertThat(resultado?.bio).isEqualTo(newBio)
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