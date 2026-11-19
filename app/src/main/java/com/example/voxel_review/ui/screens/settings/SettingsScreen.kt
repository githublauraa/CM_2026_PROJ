package com.example.voxel_review.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.settings.components.GamerPreferencesSection
import com.example.voxel_review.ui.screens.settings.components.GeneralSettingsSection
import com.example.voxel_review.ui.screens.settings.components.SettingsHeader
import com.example.voxel_review.ui.screens.settings.components.SettingsFooter
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    notificationsEnabled: Boolean,
    selectedAccent: AccentOption,
    onNotificationsChange: (Boolean) -> Unit,
    onAccentChange: (AccentOption) -> Unit,
    modifier: Modifier = Modifier,
    language: AppLanguage = AppLanguage.SPANISH,
    onLanguageChange: (AppLanguage) -> Unit = {},
    onAdminClick: (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxSize().background(VoxelBackground)) {
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.widthIn(max = 560.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                SettingsHeader(accent = selectedAccent.color, onBack = onBack)
                if (onAdminClick != null) {
                    androidx.compose.material3.Button(
                        onClick = onAdminClick,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = com.example.voxel_review.ui.theme.VoxelPrimary,
                            contentColor = VoxelBackground
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)
                    ) {
                        Text(stringResource(R.string.admin_title), style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                    }
                }
                GeneralSettingsSection(
                    accent = selectedAccent.color,
                    modifier = Modifier.padding(horizontal = 18.dp),
                    language = language,
                    notificationsEnabled = notificationsEnabled,
                    onLanguageChange = onLanguageChange,
                    onNotificationsChange = onNotificationsChange
                )
                GamerPreferencesSection(
                    selectedAccent = selectedAccent,
                    onAccentChange = onAccentChange,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
                SettingsFooter(Modifier.padding(horizontal = 18.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 750)
@Composable
private fun SettingsScreenPreview() {
    Voxel_ReviewTheme(darkTheme = true, dynamicColor = false) {
        SettingsScreen(
            onBack = {},
            notificationsEnabled = true,
            selectedAccent = AccentOption.CYAN,
            onNotificationsChange = {},
            onAccentChange = {}
        )
    }
}
