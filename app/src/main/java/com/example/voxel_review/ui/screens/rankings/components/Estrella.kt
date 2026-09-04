package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

/**
 * Muestra el icono de estrella utilizado en la pantalla de rankings.
 *
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun Estrella(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = "Estrella",
        modifier = modifier
            .height(70.dp)
            .padding(18.dp)
    )
}

/**
 * Vista previa del icono de estrella.
 */
@Preview
@Composable
fun EstrellaPreview() {
    Estrella()
}