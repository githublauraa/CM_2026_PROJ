package com.example.voxel_review.viewModels

import com.example.voxel_review.data.dtos.ReviewDto
import com.example.voxel_review.data.dtos.toReviewInfo
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.ui.screens.writeReview.WriteReviewViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WriteReviewSpoilerTest {
    private val repository = mockk<ReviewRepository>()
    private val auth = mockk<AuthRepository>()
    private lateinit var viewModel: WriteReviewViewModel

    @Before fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { auth.currentUser } returns mockk { every { uid } returns "user" }
        viewModel = WriteReviewViewModel(repository, mockk(), auth, mockk(relaxed = true))
    }
    @After fun tearDown() { Dispatchers.resetMain() }

    @Test fun `creating a spoiler review sends the flag with its content`() = runTest {
        coEvery { repository.createReview(any(), any(), any()) } returns Result.success(Unit)
        viewModel.updateReviewText("The ending")
        viewModel.updateContainsSpoilers(true)
        viewModel.publishReview("game")
        advanceUntilIdle()
        coVerify { repository.createReview("user", "game", match { it.containsSpoilers && it.content == "The ending" && it.latitude == null && it.longitude == null }) }
        assertTrue(viewModel.uiState.value.isPublished)
    }

    @Test fun `editing restores the flag and allows removing it`() = runTest {
        val review = ReviewDto(reviewId = "review", content = "The ending", containsSpoilers = true).toReviewInfo()
        viewModel.loadReview(review)
        assertTrue(viewModel.uiState.value.containsSpoilers)
        coEvery { repository.updateReview(any(), any()) } returns Result.success(Unit)
        viewModel.updateContainsSpoilers(false)
        viewModel.updateReview("review", "user", "game")
        advanceUntilIdle()
        coVerify { repository.updateReview("review", match { !it.containsSpoilers && it.content == "The ending" && it.latitude == null && it.longitude == null }) }
    }

    @Test fun `publishing with location preserves coordinates and spoiler flag`() = runTest {
        coEvery { repository.createReview(any(), any(), any()) } returns Result.success(Unit)
        viewModel.updateReviewText("Located review")
        viewModel.updateContainsSpoilers(true)
        viewModel.publishReview("game", latitude = 4.60971, longitude = -74.08175)
        advanceUntilIdle()
        coVerify { repository.createReview("user", "game", match {
            it.latitude == 4.60971 && it.longitude == -74.08175 && it.containsSpoilers
        }) }
        assertTrue(viewModel.uiState.value.isPublished)
    }

    @Test fun `legacy reviews default to no spoilers`() {
        assertFalse(ReviewDto().toReviewInfo().containsSpoilers)
    }
}
