package com.example.voxel_review.ui.screens.settings

data class SettingsState(
    val notificationsEnabled: Boolean = true,
    val selectedAccent: AccentOption = AccentOption.CYAN
)
