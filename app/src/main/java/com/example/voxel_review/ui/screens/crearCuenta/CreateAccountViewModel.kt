package com.example.voxel_review.ui.screens.crearCuenta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import com.example.voxel_review.ui.auth.AuthValidation
import com.example.voxel_review.ui.auth.authErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var pendingUserId: String?
        get() = savedStateHandle["pendingUserId"]
        set(value) { savedStateHandle["pendingUserId"] = value }
    private val _uiState = MutableStateFlow(CreateAccountState(profilePending = pendingUserId != null))
    val uiState: StateFlow<CreateAccountState> = _uiState

    fun updateBio(input: String) { if (!_uiState.value.isLoading) _uiState.update { it.copy(bio = input, fieldErrors = it.fieldErrors - "bio", errorMessage = "") } }
    fun updateUsername(input: String) { if (!_uiState.value.isLoading) _uiState.update { it.copy(username = input, fieldErrors = it.fieldErrors - "username", errorMessage = "") } }
    fun updateEmail(input: String) { if (!_uiState.value.isLoading && !_uiState.value.profilePending) _uiState.update { it.copy(email = input, fieldErrors = it.fieldErrors - "email", errorMessage = "") } }
    fun updatePassword(input: String) { if (!_uiState.value.isLoading && !_uiState.value.profilePending) _uiState.update { it.copy(password = input, fieldErrors = it.fieldErrors - "password", errorMessage = "") } }
    fun updateMostrarContrasena() { _uiState.update { it.copy(mostrarContrasena = !it.mostrarContrasena) } }
    fun updateTerminosAceptados() { if (!_uiState.value.isLoading) _uiState.update { it.copy(terminosAceptados = !it.terminosAceptados, fieldErrors = it.fieldErrors - "terms", errorMessage = "") } }

    fun createAcount(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.isLoading) return
        val errors = buildMap {
            AuthValidation.username(state.username)?.let { put("username", it) }
            if (state.bio.length > 300) put("bio", "La biografía no puede superar 300 caracteres.")
            if (!state.profilePending) {
                AuthValidation.email(state.email)?.let { put("email", it) }
                AuthValidation.password(state.password, registration = true)?.let { put("password", it) }
            }
            if (!state.terminosAceptados) put("terms", "Acepta los términos y condiciones para continuar.")
        }
        _uiState.update { it.copy(fieldErrors = errors, errorMessage = "") }
        if (errors.isNotEmpty()) return
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                if (pendingUserId == null) {
                    val result = authRepository.signUp(state.email.trim(), state.password)
                    if (result.isFailure) {
                        _uiState.update { it.copy(errorMessage = authErrorMessage(result.exceptionOrNull(), true)) }
                        return@launch
                    }
                    pendingUserId = authRepository.currentUser?.uid
                    _uiState.update { it.copy(profilePending = pendingUserId != null, password = "") }
                }
                val userId = pendingUserId
                if (userId == null || authRepository.currentUser?.uid != userId) {
                    _uiState.update { it.copy(errorMessage = "La sesión no está disponible. Vuelve a iniciar sesión para continuar.") }
                    return@launch
                }
                val result = userRepository.registerUser(state.username.trim(), state.bio.trim(), userId, null)
                if (result.isSuccess) {
                    pendingUserId = null
                    _uiState.update { it.copy(profilePending = false) }
                    onSuccess()
                } else {
                    _uiState.update { it.copy(errorMessage = "Tu cuenta ya se creó, pero falta guardar el perfil. " + authErrorMessage(result.exceptionOrNull(), true) + " Pulsa Reintentar guardar perfil.") }
                }
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = authErrorMessage(e, true)) }
            } finally { _uiState.update { it.copy(isLoading = false) } }
        }
    }
}
