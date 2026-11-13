package com.example.voxel_review.viewModels

import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.LibraryRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.StorageRepository
import com.example.voxel_review.data.repository.UserRepository
import com.example.voxel_review.ui.screens.profile.ProfileContentTab
import com.example.voxel_review.ui.screens.profile.ProfileViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelLibraryUnitTest {

    private val authRepository = mockk<AuthRepository>()
    private val storageRepository = mockk<StorageRepository>()
    private val userRepository = mockk<UserRepository>()
    private val reviewRepository = mockk<ReviewRepository>()
    private val libraryRepository = mockk<LibraryRepository>()

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { authRepository.currentUser } returns null
        viewModel = ProfileViewModel(
            authRepository = authRepository,
            storageRepository = storageRepository,
            userRepository = userRepository,
            reviewRepository = reviewRepository,
            libraryRepository = libraryRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserLibrary carga coleccion, cambia de pestania y filtra por estado`() = runTest {
        val entries = listOf(
            LibraryGameInfo("g1", "Hades", "", "Supergiant", LibraryStatus.PLAYING, 300L),
            LibraryGameInfo("g2", "Portal 2", "", "Valve", LibraryStatus.COMPLETED, 200L),
            LibraryGameInfo("g3", "Silksong", "", "Team Cherry", LibraryStatus.WANT_TO_PLAY, 100L)
        )
        coEvery { libraryRepository.getUserLibrary("user-1") } returns Result.success(entries)

        viewModel.getUserLibrary("user-1")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.selectedTab).isEqualTo(ProfileContentTab.REVIEWS)
        assertThat(viewModel.uiState.value.libraryGames).hasSize(3)
        assertThat(viewModel.uiState.value.filteredLibraryGames).hasSize(3)

        viewModel.selectProfileTab(ProfileContentTab.LIBRARY)
        assertThat(viewModel.uiState.value.selectedTab).isEqualTo(ProfileContentTab.LIBRARY)

        viewModel.selectLibraryFilter(LibraryStatus.COMPLETED)
        assertThat(viewModel.uiState.value.filteredLibraryGames).hasSize(1)
        assertThat(viewModel.uiState.value.filteredLibraryGames.first().videoGameId).isEqualTo("g2")

        viewModel.selectLibraryFilter(null)
        assertThat(viewModel.uiState.value.filteredLibraryGames).hasSize(3)
    }

    @Test
    fun `getUserLibrary en error actualiza libraryError`() = runTest {
        coEvery { libraryRepository.getUserLibrary("user-1") } returns Result.failure(RuntimeException("Fallo"))

        viewModel.getUserLibrary("user-1")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.isLibraryLoading).isFalse()
        assertThat(viewModel.uiState.value.libraryError).isEqualTo("No se pudo cargar la colección")
    }
}
