package com.example.voxel_review.ui.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.MostrarLogo
import androidx.compose.foundation.layout.Column

@Composable
fun StartHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        MostrarLogo()

        NombreApp()

        Subtitulo()
    }
}