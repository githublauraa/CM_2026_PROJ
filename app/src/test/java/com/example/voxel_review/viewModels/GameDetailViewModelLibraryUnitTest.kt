package com.example.voxel_review.viewModels

import com.example.voxel_review.data.dtos.VideoGameDto
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.repository.LibraryRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.VideoGameRepository
import com.example.voxel_review.ui.screens.GameDetail.GameDetailViewModel
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameDetailViewModelLibraryUnitTest {

    private val videoGameRepository = mockk<VideoGameRepository>()
    private val reviewRepository = mockk<ReviewRepository>()
    private val libraryRepository = mockk<LibraryRepository>()
    private val auth = mockk<FirebaseAuth>()
    private val firebaseUser = mockk<FirebaseUser>()

    private lateinit var viewModel: GameDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { firebaseUser.uid } returns "user-1"
        every { auth.currentUser } returns firebaseUser
        viewModel = GameDetailViewModel(
            videoGameRepository = videoGameRepository,
            reviewRepository = reviewRepository,
            libraryRepository = libraryRepository,
            auth = auth
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGame carga estado inicial de biblioteca y toggleLibraryStatus cambia y quita estado`() = runTest {
        val gameDto = VideoGameDto(
            videoGameId = "game-1",
            name = "Celeste",
            description = "Plataformas",
            developer = "Maddy Makes Games",
            platform = "PC",
            releaseDate = "2018",
            imageUrl = "https://example.com/celeste.jpg"
        )
        coEvery { videoGameRepository.getVideoGameById("game-1") } returns Result.success(gameDto)
        coEvery { reviewRepository.getReviewsLive("game-1") } returns flowOf(emptyList())
        coEvery { libraryRepository.getGameStatus("user-1", "game-1") } returns Result.success(LibraryStatus.WANT_TO_PLAY)
        coEvery { libraryRepository.setGameStatus("user-1", any(), LibraryStatus.PLAYING) } returns Result.success(Unit)
        coEvery { libraryRepository.removeGameFromLibrary("user-1", "game-1") } returns Result.success(Unit)

        viewModel.loadGame("game-1")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.libraryStatus).isEqualTo(LibraryStatus.WANT_TO_PLAY)

        // Cambiar a Jugando
        viewModel.toggleLibraryStatus(LibraryStatus.PLAYING)
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.libraryStatus).isEqualTo(LibraryStatus.PLAYING)
        coVerify(exactly = 1) { libraryRepository.setGameStatus("user-1", any(), LibraryStatus.PLAYING) }

        // Volver a pulsar Jugando lo elimina de la colección
        viewModel.toggleLibraryStatus(LibraryStatus.PLAYING)
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.libraryStatus).isNull()
        coVerify(exactly = 1) { libraryRepository.removeGameFromLibrary("user-1", "game-1") }
    }

    @Test
    fun `toggleLibraryStatus revierte el estado si falla el repositorio`() = runTest {
        val gameDto = VideoGameDto(
            videoGameId = "game-1",
            name = "Celeste",
            description = "Plataformas",
            developer = "Maddy Makes Games",
            platform = "PC",
            releaseDate = "2018",
            imageUrl = null
        )
        coEvery { videoGameRepository.getVideoGameById("game-1") } returns Result.success(gameDto)
        coEvery { reviewRepository.getReviewsLive("game-1") } returns flowOf(emptyList())
        coEvery { libraryRepository.getGameStatus("user-1", "game-1") } returns Result.success(null)
        coEvery {
            libraryRepository.setGameStatus("user-1", any(), LibraryStatus.COMPLETED)
        } returns Result.failure(RuntimeException("Sin conexión"))

        viewModel.loadGame("game-1")
        advanceUntilIdle()

        viewModel.toggleLibraryStatus(LibraryStatus.COMPLETED)
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.libraryStatus).isNull()
        assertThat(viewModel.uiState.value.libraryError).isEqualTo("Sin conexión")
    }
}
