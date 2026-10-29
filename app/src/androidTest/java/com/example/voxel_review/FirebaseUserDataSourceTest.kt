package com.example.voxel_review

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
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

    //codigo que quiero que se ejecute despues de terminar las pruebas
    @After
    fun tearDown() = runTest {
        //borrar usuarios
        val users = db.collection("users").get().await()
        users.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }
    }


    //primero el nombre de lo que se prueba, segundo las precondiciones (usuarios existente), tercero lo que espero que me responda(usuario correcto)
    @Test
    fun getUserById_validId_correctUser() = runTest {
        //diseñar pruebas AAA
        //Arrange (preparar)
        val id = "user_0"
        val expectedName = "username_0"
        val currentUserId= "user_1"
        //Act (ejecutar) (realizar prueba)
        val result = dataSource.getUserById(id, currentUserId)
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
        val currentUserId= "user_109209320932"
        //Act (ejecutar) (realizar prueba)
        val result = dataSource.getUserById(id, currentUserId)
        //Assert (validar)
        //resultado no puede ser nulo porque se esta buscando algo
        Truth.assertThat(result).isNull()
    }

}