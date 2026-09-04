package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.MostrarLogo

/**
 * Muestra el encabezado de la pantalla de inicio.
 * Incluye el logo, el nombre de la aplicación y su subtítulo.
 *
 * @param modifier Modificador para personalizar el componente.
 */
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