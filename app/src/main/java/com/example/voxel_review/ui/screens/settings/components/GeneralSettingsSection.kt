package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.settings.AppLanguage
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@Composable
fun GeneralSettingsSection(
    language: AppLanguage,
    accent: Color,
    notificationsEnabled: Boolean,
    onLanguageChange: (AppLanguage) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val highlight = lerp(accent, VoxelTextPrimary, 0.35f)
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionTitle(stringResource(R.string.settings_general))
        SettingsCard {
            LanguageSelector(language = language, onLanguageChange = onLanguageChange, accent = highlight)
            HorizontalDivider(modifier = Modifier.padding(start = 70.dp, end = 16.dp), color = VoxelBorder)
            SettingSwitch(
                icon = Icons.Outlined.Notifications,
                title = stringResource(R.string.notificaciones),
                checked = notificationsEnabled,
                accentColor = highlight,
                onCheckedChange = onNotificationsChange
            )
        }
    }
}
