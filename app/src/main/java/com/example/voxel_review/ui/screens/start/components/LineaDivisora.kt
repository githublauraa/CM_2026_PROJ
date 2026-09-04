package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

/**
 * Muestra una línea divisora horizontal utilizada para separar
 * visualmente elementos de la interfaz.
 *
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun LineaDivisora(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier.width(130.dp),
        color = onSurfaceVariantDark
    )
}

/**
 * Vista previa de la línea divisora.
 */
@Composable
@Preview
fun LineaDivisoraPreview() {
    LineaDivisora()
}