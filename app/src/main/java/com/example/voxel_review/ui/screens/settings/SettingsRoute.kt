package com.example.voxel_review.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by settingsViewModel.uiState.collectAsState()

    SettingsScreen(
        onBack = onBackClick,
        darkMode = false,
        notificationsEnabled = state.notificationsEnabled,
        selectedAccent = state.selectedAccent,
        onNotificationsChange = { settingsViewModel.updateNotifications(it) },
        onAccentChange = { settingsViewModel.updateAccent(it) },
        modifier = modifier
    )
}
