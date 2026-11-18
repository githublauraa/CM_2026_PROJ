package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.settings.AccentOption

@Composable
fun GamerPreferencesSection(
    selectedAccent: AccentOption,
    onAccentChange: (AccentOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionTitle(stringResource(R.string.settings_appearance))
        AccentSelector(selectedAccent = selectedAccent, onAccentChange = onAccentChange)
    }
}
