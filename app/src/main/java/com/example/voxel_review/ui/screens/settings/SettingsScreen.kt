package com.example.voxel_review.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.settings.components.GamerPreferencesSection
import com.example.voxel_review.ui.screens.settings.components.GeneralSettingsSection
import com.example.voxel_review.ui.screens.settings.components.SettingsHeader
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    selectedAccent: AccentOption,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onAccentChange: (AccentOption) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SettingsHeader(
                onBack = onBack
            )

            GeneralSettingsSection(
                color = VoxelSurfaceVariant,
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
}

@Preview(
    showBackground = false,
    backgroundColor = 0xFF0F0D1F
)
@Composable
fun SettingsScreenPreview() {

    Voxel_ReviewTheme(
        darkTheme = false,
        dynamicColor = true
    ) {

        SettingsScreen(
            onBack = {},
            darkMode = false,
            notificationsEnabled = true,
            selectedAccent = AccentOption.CYAN,
            onDarkModeChange = {},
            onNotificationsChange = {},
            onAccentChange = {}
        )
    }
}