package com.example.voxel_review.ui.screens.Splash

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import jakarta.inject.Inject
import com.example.voxel_review.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel(){

    private val _navegateHome = MutableStateFlow(false)
    val navigateHome: StateFlow<Boolean> = _navegateHome


    init{
        checkUser()
    }


    private fun checkUser() {
        if(authRepository.currentUser != null){
            _navegateHome.value = true
        } else {
            _navegateHome.value = false
        }
    }
}