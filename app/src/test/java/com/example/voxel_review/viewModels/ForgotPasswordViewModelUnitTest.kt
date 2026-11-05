package com.example.voxel_review.viewModels

import android.util.Patterns
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.ui.screens.forgotPassword.ForgotPasswordViewModel
import com.google.common.truth.Truth.assertThat
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
import com.google.common.truth.Truth.assertThat

class ForgotPasswordViewModelUnitTest {

    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var authRepository: AuthRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = mockk()
        viewModel = ForgotPasswordViewModel (authRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sendResetEmail_whenEmailIsValid_noShowError() = runTest {

        //arrange
        viewModel.updateCorreo("juan@gmail.com")

        coEvery { authRepository.sendPasswordResetEmail("juan@gmail.com") } returns Result.success(Unit)

        //act
        viewModel.sendResetEmail()
        //esperar a que todo acabe
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.isEmailSent).isTrue()
        assertThat(state.errorMessage).isEmpty()

    }


}