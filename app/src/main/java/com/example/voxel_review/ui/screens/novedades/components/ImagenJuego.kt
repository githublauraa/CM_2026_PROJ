package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
fun ImagenJuego(
    imagen: String,
    descripcion: String,
    modifier: Modifier = Modifier
) {
    ProfileImage(
        profileImage = imagen,
        size = 88,
        modifier = modifier
    )
}