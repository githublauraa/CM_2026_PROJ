package com.example.voxel_review.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.theme.VoxelBackground

@Composable
fun FondoPantalla(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoxelBackground)
    )
}

@Composable
@Preview
fun FondoPantallaPreview() {
    FondoPantalla()
}