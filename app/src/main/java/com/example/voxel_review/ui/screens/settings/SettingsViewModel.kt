package com.example.voxel_review.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.dataSource.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: SettingsPreferences
) : ViewModel() {

    private val savedColorName = preferences.getScreenColor()
    private val savedColor = AccentOption.entries.firstOrNull { it.name == savedColorName }
    private val _uiState = MutableStateFlow(SettingsState(selectedAccent = savedColor ?: AccentOption.CYAN))
    val uiState: StateFlow<SettingsState> = _uiState

    fun updateNotifications(enabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = enabled) }
    }

    fun updateAccent(accent: AccentOption) {
        preferences.saveScreenColor(accent.name)
        _uiState.update { it.copy(selectedAccent = accent) }
    }
}
