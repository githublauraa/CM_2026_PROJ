package com.example.voxel_review.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.pressBack
import com.example.voxel_review.MainActivity
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import com.example.voxel_review.navigation.AppScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import kotlinx.coroutines.runBlocking
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

@HiltAndroidTest
class LoginAndFollowUserE2ETest {


    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {

        }

        val authDataSource = AuthRemoteDataSource(Firebase.auth)
        val userRemoteDataSource = UserFirestoreDataSourceImpl(Firebase.firestore)
        authRepository = AuthRepository(authDataSource)
        userRepository = UserRepository(userRemoteDataSource, authRepository)

        //usuario registrado
        runBlocking {
            authRepository.signUp("prueba@prueba.com", "123456")
            authRepository.signIn("prueba@prueba.com", "123456")


            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                userRepository.registerUser(
                    nombreUsuario = "prueba",
                    bio = null,
                    userId = userId,
                    photoUrl = null
                )
            }

        }
    }


    @Test
    fun loginUser_followUser() {
        //llenar campos
        composeRule.onNodeWithTag("txtEmail").performTextInput("prueba@prueba.com")
        composeRule.onNodeWithTag("txtPassword").performTextInput("123456")

        //dar click al boton de iniciar sesion
        composeRule.onNodeWithTag("btnIniciarSesion").performClick()

        //navegar a la pantalla de home
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodesWithTag("homeScreen").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("homeScreen").assertIsDisplayed()

        //seleccionar el primer perfil
        composeRule.onAllNodesWithTag("profile").onFirst().performScrollTo().performClick()

        composeRule.waitForIdle()

        Thread.sleep(5_000)

        //seguir al usuario y verificar que sume uno en seguidores
        composeRule.onNodeWithTag("btnFollow").performClick()
        composeRule.waitForIdle()

        Thread.sleep(5_000)

        //ir hacia atras
        pressBack()

        //ir a la pantalla de de novedades y reseñas
        //navegar a la pantalla de descubrir
        composeRule.onNodeWithTag("nav_${AppScreen.Reviews.route}").performClick()
        composeRule.onNodeWithTag("novedades").assertIsDisplayed()
        Thread.sleep(7_000)
        composeRule.onNodeWithTag("btnSiguiendo").performClick()
        composeRule.waitForIdle()

        Thread.sleep(15_000)

    }


    @After
    fun tearDown() = runBlocking {
        val user = Firebase.auth.currentUser ?: return@runBlocking
        val userRef = Firebase.firestore
            .collection("users")
            .document(user.uid)

        // Eliminar los documentos de following.
        val followers = userRef.collection("followers").get().await()

        for (document in followers.documents) {
            document.reference.delete().await()
        }

        // Eliminar el perfil y la cuenta.
        userRef.delete().await()
        user.delete().await()
        Firebase.auth.signOut()
    }

}