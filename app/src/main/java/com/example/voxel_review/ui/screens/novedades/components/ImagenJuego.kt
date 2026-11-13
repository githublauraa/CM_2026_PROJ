package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.utils.GameImage

@Composable
fun ImagenJuego(
    imagen: String,
    descripcion: String,
    modifier: Modifier = Modifier
) {
    GameImage(
        imageUrl = imagen,
        contentDescription = descripcion,
        modifier = modifier.size(88.dp).clip(CircleShape)
    )
}
