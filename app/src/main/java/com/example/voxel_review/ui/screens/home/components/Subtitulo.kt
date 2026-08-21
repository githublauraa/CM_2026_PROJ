package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun Subtitulo(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.explora_rese_a_crea),
        color = VoxelTextSecondary,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
@Preview
fun SubtituloPreview() {
    Subtitulo()
}