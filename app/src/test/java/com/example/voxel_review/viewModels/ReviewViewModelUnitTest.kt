package com.example.voxel_review.viewModels

import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.ui.screens.review.ReviewViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReviewViewModelUnitTest {

    private val reviewRepository = mockk<ReviewRepository>()
    private lateinit var viewModel: ReviewViewModel
    private val review = ReviewInfo(
        idResenia = "review-1",
        imagenJuego = "",
        tituloJuego = "",
        desarrollador = "",
        tituloDescripcion = "",
        descripcion = "",
        ratingGeneral = 0f,
        ratingJugabilidad = 0f,
        ratingGraficos = 0f,
        ratingHistoria = 0f,
        imagenUsuario = "",
        cantidadLikes = 3,
        likedByCurrentUser = true,
        cantidadDislikes = 1
    )
    private val liveReview = MutableStateFlow<ReviewInfo?>(review)
    private val writeResult = CompletableDeferred<Result<Unit>>()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { reviewRepository.getSelectedReviewLive("game-1", review.idResenia) } returns liveReview
        coEvery {
            reviewRepository.toggleReviewReaction(review.idResenia, "user-1", true)
        } coAnswers { writeResult.await() }

        viewModel = ReviewViewModel(reviewRepository)
        viewModel.getGameReviews("game-1", review.idResenia)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun switchToDislike_updatesImmediatelyAndWaitsForLiveConfirmation() = runTest {
        runCurrent()

        viewModel.toggleReviewReaction(review.idResenia, "user-1", dislike = true)
        viewModel.toggleReviewReaction(review.idResenia, "user-1", dislike = true)
        runCurrent()

        val expected = review.copy(
            likedByCurrentUser = false,
            dislikedByCurrentUser = true,
            cantidadLikes = 2,
            cantidadDislikes = 2
        )
        assertThat(viewModel.uiState.value.review).isEqualTo(expected)
        assertThat(viewModel.uiState.value.isUpdatingReaction).isTrue()
        coVerify(exactly = 1) { reviewRepository.toggleReviewReaction(review.idResenia, "user-1", true) }

        writeResult.complete(Result.success(Unit))
        runCurrent()
        assertThat(viewModel.uiState.value.review).isEqualTo(expected)

        liveReview.value = expected
        runCurrent()
        assertThat(viewModel.uiState.value.review).isEqualTo(expected)
        assertThat(viewModel.uiState.value.isUpdatingReaction).isFalse()
    }

    @Test
    fun failedDislike_restoresLatestLiveCountsAndKeepsReviewVisible() = runTest {
        runCurrent()

        viewModel.toggleReviewReaction(review.idResenia, "user-1", dislike = true)
        runCurrent()

        val latest = review.copy(cantidadLikes = 4)
        liveReview.value = latest
        runCurrent()
        assertThat(viewModel.uiState.value.review?.cantidadLikes).isEqualTo(3)

        writeResult.complete(Result.failure(Exception("Error de red")))
        runCurrent()

        val state = viewModel.uiState.value
        assertThat(state.review).isEqualTo(latest)
        assertThat(state.isUpdatingReaction).isFalse()
        assertThat(state.reactionError).isEqualTo("Error de red")
        assertThat(state.errorMessage).isNull()
    }
}
