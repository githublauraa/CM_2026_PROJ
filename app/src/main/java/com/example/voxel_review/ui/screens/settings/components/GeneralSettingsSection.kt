package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.settings.*
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun GeneralSettingsSection(
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle("AJUSTES GENERALES")

        SettingSwitch(
            symbol = "☾",
            title = "Modo Oscuro",
            checked = darkMode,
            accentColor = VoxelPrimary,
            onCheckedChange = onDarkModeChange
        )

        SettingSwitch(
            iconRes = R.drawable.campana_notificaciones,
            title = "Notificaciones",
            checked = notificationsEnabled,
            accentColor = VoxelSecondary,
            onCheckedChange = onNotificationsChange
        )
    }
}
@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun GeneralSettingsSectionPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        GeneralSettingsSection(
            darkMode = true,
            notificationsEnabled = true,
            onDarkModeChange = {},
            onNotificationsChange = {}
        )
    }
}