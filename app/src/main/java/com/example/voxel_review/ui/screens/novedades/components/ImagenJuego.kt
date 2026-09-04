package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Muestra la imagen de un juego con un tamaño y bordes redondeados definidos.
 *
 * @param imagen Recurso drawable correspondiente a la imagen del juego.
 * @param descripcion Descripción utilizada para accesibilidad.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun ImagenJuego(
    imagen: Int,
    descripcion: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(imagen),
        contentDescription = descripcion,
        modifier = modifier
            .width(88.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}