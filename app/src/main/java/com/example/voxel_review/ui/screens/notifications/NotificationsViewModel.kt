package com.example.voxel_review.ui.screens.notifications

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoNotifications.LocalDestacadasProvider
import com.example.voxel_review.data.InfoNotifications.LocalSugerenciasProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel encargado de la lógica de negocio y gestión de estado para las notificaciones.
 */
class NotificationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())

    val uiState: StateFlow<NotificationsState> = _uiState

    fun getAllNotifications() {
        _uiState.value = NotificationsState(
            highlightedNotifications = LocalDestacadasProvider.destacadas,
            followSuggestions = LocalSugerenciasProvider.sugerenciasSeguimiento
        )
    }

    init {
        getAllNotifications()
    }
}