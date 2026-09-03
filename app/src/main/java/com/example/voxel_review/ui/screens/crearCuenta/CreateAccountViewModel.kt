package com.example.voxel_review.ui.screens.crearCuenta

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class CreateAccountViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(CreateAccountState())
    val uiState: StateFlow<CreateAccountState> = _uiState

    fun updateUsername(input: String){
        _uiState.update {
            it.copy(userName = input)
        }
    }

    fun updateEmail(input: String){
        _uiState.update {
            it.copy(email = input)
        }
    }

    fun updatePassword(input: String){
        _uiState.update {
            it.copy(password = input)
        }
    }

    fun updateMostrarContrasena(){
        _uiState.update {
            it.copy(mostrarContrasena = !it.mostrarContrasena)
        }
    }

    fun updateTerminosAceptados(){
        _uiState.update {
            it.copy(terminosAceptados = !it.terminosAceptados)
        }
    }

    fun login() : Boolean{
        if (
            _uiState.value.userName.isEmpty() ||
            _uiState.value.email.isEmpty()
        ){
            _uiState.update {
                it.copy(errorMessage = "Por favor, completa todos los campos")
            }
            return false
        }
        if (
            _uiState.value.password.length < 6
        ){
            _uiState.update {
                it.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
            }
            return false
        }

        if (!_uiState.value.terminosAceptados){
            _uiState.update {
                it.copy(errorMessage = "Por favor, acepta los términos y condiciones")
            }
            return false
        }

        _uiState.update {
            it.copy(errorMessage = "")
        }
        return true
    }


}