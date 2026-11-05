package com.example.voxel_review.viewModels

import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.ui.screens.review.ReviewViewModel
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

class ReviewViewModelUnitTest {

    private lateinit var viewModel: ReviewViewModel
    private lateinit var reviewRepository: ReviewRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        reviewRepository = mockk()
        viewModel = ReviewViewModel(reviewRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sendOrDeleteReviewLike_success_callsRepositoryCorrectly() = runTest {
        //Arrange
        val reviewId = "1"
        val userId = "1"

        coEvery { reviewRepository.sendOrDeleteReviewLike(any(), any()) } returns Result.success(Unit)

        //act

        viewModel.sendOrDeleteReviewLike(reviewId, userId)
        //esperar a que todo acabe
        advanceUntilIdle()
        //
        val state = viewModel.uiState.value
        assertThat(state.errorMessage).isNull()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sendOrDeleteReviewLike_failure_updatesErrorMessage() = runTest {
        //arrange
        val reviewId = "1"
        val userId = "1"
        coEvery { reviewRepository.sendOrDeleteReviewLike(any(), any()) } returns Result.failure(Exception("No se pudo actualizar el like"))

        //act
        viewModel.sendOrDeleteReviewLike(reviewId,userId)
        //esperar a que todo acabe
        advanceUntilIdle()

        //
        val state = viewModel.uiState.value
        assertThat(state.errorMessage).isNotEmpty()
    }



}