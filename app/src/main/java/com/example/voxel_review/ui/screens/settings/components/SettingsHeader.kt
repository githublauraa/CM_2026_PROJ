package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun SettingsHeader(modifier: Modifier = Modifier, onBack: () -> Unit) {
    ScreenHeader("Configuración", onBack, modifier)
}
