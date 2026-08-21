package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun Descripcion(
    descripcion: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = descripcion,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = VoxelTextSecondary,
        modifier = modifier
    )
}

@Composable
@Preview
fun DescripcionPreview() {
    Descripcion("Descripcion")
}