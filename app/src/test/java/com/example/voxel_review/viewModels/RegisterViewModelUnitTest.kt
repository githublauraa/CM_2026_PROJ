package com.example.voxel_review.viewModels

import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class RegisterViewModelUnitTest {

    private lateinit var viewModel: CreateAccountViewModel

    private lateinit var authRepository: AuthRepository

    private lateinit var userRepository: UserRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = mockk()
        userRepository = mockk()
        viewModel = CreateAccountViewModel(authRepository, userRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun register_success_CreateUserAndUpdateUI() = runTest {
        // Arrange
        viewModel.updateUsername("Juan")
        viewModel.updateEmail("juan@voxel.com")
        viewModel.updatePassword("123456")
        viewModel.updateBio("Hola, soy Juan")
        viewModel.updateTerminosAceptados()

        var registroExitoso = false

        coEvery { authRepository.signUp(any(), any()) } returns Result.success(Unit)

        coEvery { authRepository.currentUser?.uid } returns "1"

        coEvery { userRepository.registerUser(nombreUsuario = any(), bio = any(), userId = any(), photoUrl = any()) } returns Result.success(Unit)

        // Act
        viewModel.createAcount {
            registroExitoso = true
        }
        //esperar a que todo acabe
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertThat(state.errorMessage).isEmpty()
        assertThat(state.mostrarMensaje).isFalse()
        assertThat(registroExitoso).isTrue()
    }



    //correo en uso
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun register_alredyUsedEmail_showError() = runTest {

        // Arrange
        // Permite esperar a que el VM ejecute onSuccess antes de verificar el resultado.
        var registroExitoso = false

        viewModel.updateUsername("Juan")
        viewModel.updateEmail("juan@voxel.com")
        viewModel.updatePassword("123456")
        viewModel.updateBio("Hola, soy Juan")
        viewModel.updateTerminosAceptados()

        coEvery { authRepository.signUp(any(), any()) } returns Result.failure(Exception("El correo electrónico ya está en uso"))


        // Act
        viewModel.createAcount {
            registroExitoso = true
        }
        //esperar a que todo acabe
        advanceUntilIdle()



        val state = viewModel.uiState.value

        assertThat(registroExitoso).isFalse()
        assertThat(state.errorMessage).isNotEmpty()
    }



}