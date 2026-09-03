package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.settings.AccentOption
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun GamerPreferencesSection(
    selectedAccent: AccentOption,
    onAccentChange: (AccentOption) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle("PREFERENCIAS GAMER")

        AccentSelector(
            selectedAccent = selectedAccent,
            onAccentChange = onAccentChange
        )

        SettingsInfoCard()
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun GamerPreferencesSectionPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        GamerPreferencesSection(
            selectedAccent = AccentOption.PURPLE,
            onAccentChange = {}
        )
    }
}