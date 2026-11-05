package com.example.voxel_review.viewModels

import com.example.voxel_review.data.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import com.example.voxel_review.ui.screens.start.StartViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class LoginViewModelUnitTest {

    private lateinit var  viewModel: StartViewModel
    private lateinit var authRepository: AuthRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = mockk()
        viewModel = StartViewModel(authRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_success_loginUserAndUpdateUI() = runTest {
        //arrange
        viewModel.updateCorreo("juan@voxel.com")
        viewModel.updatePassword("123456")

        var registroExitoso = false

        coEvery { authRepository.signIn(any(), any()) } returns Result.success(Unit)

        //act
        viewModel.login {
            registroExitoso = true
        }
        //esperar a que todo acabe
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.loading).isFalse()
        assertThat(state.mostrarMensaje).isFalse()
        assertThat(state.errorMessage).isEmpty()

    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_incorrectCredential_showError() = runTest {
        viewModel.updateCorreo("juan@voxel.com")
        viewModel.updatePassword("123456")

        var registroExitoso = false

        coEvery { authRepository.signIn(any(), any()) } returns Result.failure(Exception("Credenciales incorrectas"))

        //act
        viewModel.login {
            registroExitoso = true
        }
        //esperar a que todo acabe
        advanceUntilIdle()


        val state = viewModel.uiState.value
        assertThat(state.loading).isFalse()
        assertThat(state.mostrarMensaje).isTrue()
        assertThat(state.errorMessage).isNotEmpty()
    }



}