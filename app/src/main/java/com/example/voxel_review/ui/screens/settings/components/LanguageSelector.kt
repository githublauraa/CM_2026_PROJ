package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.settings.AppLanguage
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun LanguageSelector(
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
    accent: Color = VoxelPrimary
) {
    var showLanguages by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth()
            .clickable(role = Role.Button) { showLanguages = true }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIconTile(accent = accent) {
            Icon(Icons.Outlined.Language, contentDescription = null, tint = accent)
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = stringResource(R.string.settings_language),
                style = MaterialTheme.typography.titleMedium,
                color = VoxelTextPrimary
            )
            Text(
                text = language.label,
                style = MaterialTheme.typography.bodyMedium,
                color = VoxelTextSecondary
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = VoxelTextSecondary
        )
    }

    if (showLanguages) {
        LanguageDialog(
            language = language,
            accent = accent,
            onDismiss = { showLanguages = false },
            onSelect = { selected ->
                showLanguages = false
                if (selected != language) onLanguageChange(selected)
            }
        )
    }
}

@Composable
private fun LanguageDialog(
    language: AppLanguage,
    accent: Color,
    onDismiss: () -> Unit,
    onSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Outlined.Language, contentDescription = null, tint = accent) },
        title = { Text(stringResource(R.string.settings_language)) },
        text = {
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AppLanguage.entries.forEach { option ->
                    LanguageOption(option, selected = language == option, accent = accent, onClick = { onSelect(option) })
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), color = accent)
            }
        },
        containerColor = VoxelSurface,
        titleContentColor = VoxelTextPrimary,
        textContentColor = VoxelTextPrimary,
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
private fun LanguageOption(
    language: AppLanguage,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) VoxelSurfaceVariant else VoxelSurface)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = accent, unselectedColor = VoxelTextSecondary)
        )
        Text(language.label, color = VoxelTextPrimary, style = MaterialTheme.typography.bodyLarge)
    }
}
