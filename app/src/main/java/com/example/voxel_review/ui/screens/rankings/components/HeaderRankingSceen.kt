package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MensajeSuperior()
            Estrella()
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotonScreen(
                textoBoton = "Críticos",
                seleccionado = seleccionado,
                colorFondo = VoxelSurface,
                colorSeleccionado = VoxelPrimary,
                colorTexto = Color.Gray,
                colorBorde = VoxelPrimary,
                onClick = { onChangeSeleccionado(true) }
            )

            BotonScreen(
                textoBoton = "Juegos",
                seleccionado = !seleccionado,
                colorFondo = VoxelSurface,
                colorSeleccionado = VoxelPrimary,
                colorTexto = Color.Gray,
                colorBorde = Color.Gray,
                onClick = { onChangeSeleccionado(false) }
            )
        }
    }
}