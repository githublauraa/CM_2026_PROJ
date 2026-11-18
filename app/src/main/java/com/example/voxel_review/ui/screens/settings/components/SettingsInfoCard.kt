package com.example.voxel_review.ui.screens.settings.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme
import com.example.voxel_review.ui.theme.VoxelTextSecondary
@Composable
fun SettingsInfoCard() {

    SettingsCard(
        color = VoxelSurfaceVariant
    ) {
        Text(
            text = stringResource(R.string.settings_terms),
            color = VoxelTextSecondary,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(R.string.settings_about),
            color = VoxelTextSecondary,
            fontSize = 11.sp,
            lineHeight = 15.sp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun SettingsInfoCardPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SettingsInfoCard()
    }
}