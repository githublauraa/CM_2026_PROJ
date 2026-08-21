package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun LineaDivisora(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier.width(130.dp),
        color = VoxelTextSecondary
    )
}

@Composable
@Preview
fun LineaDivisoraPreview() {
    LineaDivisora()
}