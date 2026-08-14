package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun TarjetaJuego(
    imagen: Int,
    etiqueta: String,
    nombre: String,
    descripcion: String,
    autor: String,
    calificacion: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(145.dp)
            .background(
                color = colorResource(R.color.Voxel_moradooo),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(R.color.Voxel_morado_oscuro),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ImagenJuego(
            imagen = imagen,
            descripcion = nombre
        )

        Spacer(
            modifier = Modifier.width(15.dp)
        )

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
                    color = colorResource(R.color.Voxel_rosado),
                    fontSize = 10.sp
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "☆ $calificacion",
                    color = colorResource(R.color.Voxel_amarillo),
                    fontSize = 13.sp
                )
            }

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = nombre,
                color = colorResource(R.color.white),
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = descripcion,
                color = colorResource(R.color.Voxel_secundario),
                fontSize = 12.sp,
                lineHeight = 14.sp
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "●  $autor",
                color = colorResource(R.color.Voxel_secundario),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
@Preview
fun TarjetaJuegoPreview(){
    TarjetaJuego(
        imagen = R.drawable.logo_fortnite,
        etiqueta = "Reseña",
        nombre = "cyber",
        descripcion = "Cyber es un juego ...",
        autor = "andres",
        calificacion = "4.5"
    )
}