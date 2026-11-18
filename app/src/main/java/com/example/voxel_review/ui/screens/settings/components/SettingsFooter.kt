package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun SettingsFooter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalDivider(color = VoxelBorder)
        Text(
            text = stringResource(R.string.settings_terms),
            modifier = Modifier.padding(top = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = VoxelTextSecondary,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.settings_about),
            modifier = Modifier.padding(horizontal = 18.dp),
            style = MaterialTheme.typography.bodySmall,
            fontFamily = FontFamily.SansSerif,
            color = VoxelTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}
