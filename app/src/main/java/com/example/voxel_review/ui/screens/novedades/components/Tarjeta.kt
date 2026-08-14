package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                color = Color(0xFF18122E),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF362762),
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
                    color = Color(0xFFFF3C9E),
                    fontSize = 10.sp
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "☆ $calificacion",
                    color = Color.Yellow,
                    fontSize = 13.sp
                )
            }

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = nombre,
                color = Color.White,
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = descripcion,
                color = Color(0xFFA49CB9),
                fontSize = 12.sp,
                lineHeight = 14.sp
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "●  $autor",
                color = Color(0xFF9189A8),
                fontSize = 11.sp
            )
        }
    }
}