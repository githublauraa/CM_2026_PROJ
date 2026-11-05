package com.example.voxel_review.viewModels

import com.example.voxel_review.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import com.example.voxel_review.ui.screens.editProfile.EditProfileViewModel
import com.google.firebase.auth.FirebaseAuth
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
import com.example.voxel_review.data.info.profile.ProfileInfo

class EditProfileViewModelUnitTest {

    private lateinit var viewModel: EditProfileViewModel

    private lateinit var userRepository: UserRepository

    private lateinit var firebaseAuth: FirebaseAuth

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        firebaseAuth = mockk()
        userRepository = mockk()
        viewModel = EditProfileViewModel(userRepository, firebaseAuth)
    }


    //probar que guarda de manera correcta el perfil
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveProfile_whenProfileLoaded_savesUpdatedBioAndUsername() = runTest {
        //arrange
        val profile = ProfileInfo(
            pfp = "",
            id = "1",
            nick = "oldUsername",
            numResenias = 0,
            promedio = 0f,
            likes = 0,
            biografia = "oldBio"
        )

        coEvery { firebaseAuth.currentUser?.uid } returns "1"

        coEvery {  userRepository.getUserById("1")} returns Result.success(profile)

        viewModel.loadProfile()
        advanceUntilIdle()

        viewModel.updateBio("newBio")
        viewModel.updateUsername("newUsername")


        coEvery { userRepository.updateUserBio("1", "newBio") } returns Result.success(Unit)

        coEvery {  userRepository.updateUsername("1", "newUsername")} returns Result.success(Unit)

        //act
        viewModel.saveProfile()
        //esperar a que todo acabe
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertThat(state.isProfileLoaded).isTrue()
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).isNull()
        assertThat(state.isSaved).isTrue()
    }

}