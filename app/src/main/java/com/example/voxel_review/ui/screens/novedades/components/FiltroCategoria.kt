package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.backgroundDark
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import androidx.compose.foundation.clickable
import com.example.voxel_review.ui.theme.VoxelSurface


@Composable
fun FiltroCategoria(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = texto,
        color = onSurfaceVariantDark,
        fontSize = 10.sp,
        modifier = modifier
            .background(
                color = if (seleccionado) VoxelPrimary else VoxelSurface,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (seleccionado) VoxelPrimary else VoxelSurface,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 17.dp,
                vertical = 8.dp
            )
    )
}

@Composable
@Preview
fun FiltroCategoriaPreview() {
    FiltroCategoria(
        texto = "Categoria",
        seleccionado = true,
        onClick = {}
    )
}