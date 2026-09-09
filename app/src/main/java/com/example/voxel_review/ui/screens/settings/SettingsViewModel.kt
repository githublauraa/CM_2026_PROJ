package com.example.voxel_review.ui.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState

    fun updateNotifications(enabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = enabled) }
    }

    fun updateAccent(accent: AccentOption) {
        _uiState.update { it.copy(selectedAccent = accent) }
    }
}
