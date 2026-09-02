package com.example.voxel_review.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class StartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StartState())
    val uiState: StateFlow<StartState> = _uiState

    fun updateUsername(input : String){
        _uiState.update {
            it.copy(usuario = input)
        }
    }

    fun updatePassword(input : String){
        _uiState.update {
            it.copy(password = input)
        }
    }

    fun updateMostrarContrasena() {
        _uiState.update {
            it.copy(mostrarContrasena = !it.mostrarContrasena)
        }
    }

    fun login(): Boolean {
        if (
            _uiState.value.usuario.isEmpty() ||
            _uiState.value.password.isEmpty()
        ){
            _uiState.update {
                it.copy(errorMessage = "Por favor, completa todos los campos")
            }
            return false
        }
        if (_uiState.value.password.length < 6){
            _uiState.update {
                it.copy(errorMessage = "La contraseña debe ser minimo de 6 caracteres")
            }
            return false
        }


        _uiState.update {
            it.copy(errorMessage = "")
        }
        return true
    }
}