package com.example.voxel_review.ui.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by settingsViewModel.uiState.collectAsState()

    val language = AppLanguage.fromTag(LocalConfiguration.current.locales[0]?.toLanguageTag())

    SettingsScreen(
        onBack = onBackClick,
        notificationsEnabled = state.notificationsEnabled,
        selectedAccent = state.selectedAccent,
        onNotificationsChange = { settingsViewModel.updateNotifications(it) },
        onAccentChange = { settingsViewModel.updateAccent(it) },
        language = language,
        onLanguageChange = { selected ->
            if (selected != language) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(selected.tag))
            }
        },
        modifier = modifier
    )
}
