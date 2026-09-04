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
import androidx.compose.ui.graphics.Color
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@Composable
fun GeneralSettingsSection(
    color: Color,
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle("AJUSTES GENERALES")

        SettingSwitch(
            color = color,
            symbol = "☾",
            title = "Modo Oscuro",
            checked = darkMode,
            accentColor = VoxelTextPrimary,
            onCheckedChange = {}
        )

        SettingSwitch(
            color = color,
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
            color = VoxelPrimary,
            darkMode = true,
            notificationsEnabled = true,
            onNotificationsChange = {}
        )
    }
}