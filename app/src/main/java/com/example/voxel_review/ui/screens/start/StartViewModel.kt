package com.example.voxel_review.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.ui.auth.AuthValidation
import com.example.voxel_review.ui.auth.authErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException

@HiltViewModel
class StartViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(StartState())
    val uiState: StateFlow<StartState> = _uiState

    fun updateCorreo(input: String) {
        if (!_uiState.value.isLoading) _uiState.update { it.copy(correo = input, fieldErrors = it.fieldErrors - "email", errorMessage = "") }
    }
    fun updatePassword(input: String) {
        if (!_uiState.value.isLoading) _uiState.update { it.copy(password = input, fieldErrors = it.fieldErrors - "password", errorMessage = "") }
    }
    fun updateMostrarContrasena() { _uiState.update { it.copy(mostrarContrasena = !it.mostrarContrasena) } }

    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.isLoading) return
        val errors = buildMap {
            AuthValidation.email(state.correo)?.let { put("email", it) }
            AuthValidation.password(state.password)?.let { put("password", it) }
        }
        _uiState.update { it.copy(fieldErrors = errors, errorMessage = "") }
        if (errors.isNotEmpty()) return
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val result = authRepository.signIn(state.correo.trim(), state.password)
                if (result.isSuccess) {
                    _uiState.update { it.copy(password = "") }
                    onSuccess()
                } else _uiState.update { it.copy(errorMessage = authErrorMessage(result.exceptionOrNull())) }
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = authErrorMessage(e)) }
            } finally { _uiState.update { it.copy(isLoading = false) } }
        }
    }
}
