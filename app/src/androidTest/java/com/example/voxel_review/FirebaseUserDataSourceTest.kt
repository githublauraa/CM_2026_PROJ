package com.example.voxel_review

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import kotlinx.coroutines.tasks.await
import com.google.common.truth.Truth
import kotlin.time.Duration.Companion.minutes

class FirebaseUserDataSourceTest {

    //base de datos
    private val db = Firebase.firestore


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


    //primero el nombre de lo que se prueba, segundo las precondiciones (usuarios existente), tercero lo que espero que me responda(usuario correcto)
    @Test
    fun getUserById_validId_correctUser() = runTest {
        //usar el emulador
        db.useEmulator("10.0.2.2",8080)

        val dataSource = UserFirestoreDataSourceImpl(db)

        //las pruebas deben ser replicables  (hacer click 30 veces y deberia dar el mismo resultado)
        //autonomas (no deben depender del resultado de otras pruebas)

        //insertar usuarios


        val batch = db.batch()
        repeat(10){i ->
            val user = generateUser(i)
            batch.set(db.collection("users").document(user.userId), user)

        }
        println("PASO 3: Insertando usuarios")
        batch.commit().await()
        println("PASO 4: Usuarios insertados")


        //diseñar pruebas AAA
        //Arrange (preparar)
        val id = "user_0"
        val expectedName = "username_0"
        val currentUserId= "user_1"
        //Act (ejecutar) (realizar prueba)
        println("PASO 5: Consultando usuario")
        val result = dataSource.getUserById(id, currentUserId)
        //Assert (validar)
        //resultado no puede ser nulo porque se esta buscando algo
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result.username).isEqualTo(expectedName)
        Truth.assertThat(result.userId).isEqualTo(id)
        Truth.assertThat(result.followed).isFalse()


        //borrar usuario

        val users = db.collection("users").get().await()
        users.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }

    }
}