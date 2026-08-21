package com.example.voxel_review.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.settings.components.GamerPreferencesSection
import com.example.voxel_review.ui.screens.settings.components.GeneralSettingsSection
import com.example.voxel_review.ui.screens.settings.components.SettingsHeader
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun SettingsScreen(
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    selectedAccent: AccentOption,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onAccentChange: (AccentOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SettingsHeader()

        GeneralSettingsSection(
            darkMode = darkMode,
            notificationsEnabled = notificationsEnabled,
            onDarkModeChange = onDarkModeChange,
            onNotificationsChange = onNotificationsChange
        )

        GamerPreferencesSection(
            selectedAccent = selectedAccent,
            onAccentChange = onAccentChange
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun SettingsScreenPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SettingsScreen(
            darkMode = true,
            notificationsEnabled = true,
            selectedAccent = AccentOption.CYAN,
            onDarkModeChange = {},
            onNotificationsChange = {},
            onAccentChange = {}
        )
    }
}
