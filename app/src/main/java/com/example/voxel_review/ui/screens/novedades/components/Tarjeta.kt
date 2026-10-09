
package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White

@Composable
fun TarjetaJuego(
    imagen: String,
    etiqueta: String,
    nombre: String,
    descripcion: String,
    autor: String,
    calificacion: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .height(145.dp)
            .background(
                color = VoxelBackground,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = VoxelSurfaceVariant,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ImagenJuego(
            imagen = imagen,
            descripcion = nombre
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = etiqueta,
                    color = VoxelSecondary,
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = if (calificacion == "Sin calificaciones") {
                        calificacion
                    } else {
                        "☆ $calificacion"
                    },
                    color = VoxelAccentYellow,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = nombre,
                color = White,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = descripcion,
                color = VoxelTextSecondary,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "●  $autor",
                color = VoxelTextSecondary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun TarjetaJuegoPreview() {
    TarjetaJuego(
        imagen = "",
        etiqueta = "VIDEOJUEGO",
        nombre = "Starfield",
        descripcion = "Explora una galaxia llena de planetas.",
        autor = "Bethesda Game Studios",
        calificacion = "Sin calificaciones",
        onClick = {}
    )
}
