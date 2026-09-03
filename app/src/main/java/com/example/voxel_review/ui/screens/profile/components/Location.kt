package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun Location(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.ubicacion),
        color = VoxelTextSecondary,
        modifier = modifier.padding(8.dp)
    )
}