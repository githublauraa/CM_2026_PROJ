package com.example.voxel_review.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.voxel_review.MainActivity
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.compose.ui.test.assertIsOn

@HiltAndroidTest
class RegisterNewUserE2E {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp(){
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
//        runBlocking {
//            authRepository.signUp("prueba@gmail.com","123456")
//            authRepository.logOut()
//        }

    }

    //
    @Test
    fun registerUser_shortPassword_showMessage() {
        composeRule.onNodeWithTag("btnCrearCuenta").performClick()
        //escribir en los campos
        composeRule.onNodeWithTag("txtNombre").performTextInput("nuevo")
        composeRule.onNodeWithTag("txtBio").performTextInput("bio nuevo")
        composeRule.onNodeWithTag("txtCorreo").performTextInput("nuevo@gmail.com")
        composeRule.onNodeWithTag("txtContrasenia").performTextInput("1234")
        closeSoftKeyboard()
        composeRule.onNodeWithTag("chkTerminos").performClick()
        //intentar inicio de sesion
        composeRule.onNodeWithTag("btnRegistrarse").performClick()
        //mostrar mensaje de error
        composeRule.onNodeWithTag("mensajeError").assertIsDisplayed()
        //intentar con contraseña valida
        composeRule.onNodeWithTag("txtContrasenia").performTextReplacement("123456")
        closeSoftKeyboard()
        composeRule.onNodeWithTag("chkTerminos").assertIsOn()
        //crear cuenta
        composeRule.onNodeWithTag("btnRegistrarse").performClick()
        //esperar un tiempo
        composeRule.waitUntil(timeoutMillis = 5000){
            composeRule.onAllNodesWithTag("homeScreen").fetchSemanticsNodes().isNotEmpty()
        }
        //navegar a la pantalla de descubrir
        composeRule.onNodeWithTag("homeScreen").assertIsDisplayed()

        //navegar a la pantalla de descubrir



    }

    @After
    fun tearDown() = runTest{
        val user = Firebase.auth.currentUser
        if (user != null){
            Firebase.auth.signOut()
        }
        user?.delete()?.await()
    }
}