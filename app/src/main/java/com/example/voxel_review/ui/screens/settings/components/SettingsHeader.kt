package com.example.voxel_review.ui.screens.settings.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun SettingsHeader(modifier: Modifier = Modifier, onBack: () -> Unit) {
    ScreenHeader(stringResource(R.string.settings_title), onBack, modifier)
}
