package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface

/**
 * Encabezado de la pantalla de rankings.
 * Muestra el título, el icono de estrella y los botones para cambiar
 * entre el ranking de críticos y el ranking de juegos.
 *
 * @param seleccionado Indica si la opción de críticos está seleccionada.
 * @param onChangeSeleccionado Acción ejecutada al cambiar la opción seleccionada.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun HeaderRankingSceen(
    seleccionado: Boolean,
    onChangeSeleccionado: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        MensajeSuperior(
            modifier = Modifier.align(Alignment.TopStart)
        )

        Estrella(
            modifier = Modifier.align(Alignment.TopEnd)
        )

        BotonScreen(
            textoBoton = "Críticos",
            seleccionado = seleccionado,
            colorFondo = VoxelSurface,
            colorSeleccionado = VoxelPrimary,
            colorTexto = Color.Gray,
            colorBorde = VoxelPrimary,
            onClick = {
                onChangeSeleccionado(true)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 70.dp,
                    start = 18.dp
                )
        )

        BotonScreen(
            textoBoton = "Juegos",
            seleccionado = !seleccionado,
            colorFondo = VoxelSurface,
            colorSeleccionado = VoxelPrimary,
            colorTexto = Color.Gray,
            colorBorde = Color.Gray,
            onClick = {
                onChangeSeleccionado(false)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 70.dp,
                    start = 125.dp
                )
        )
    }
}