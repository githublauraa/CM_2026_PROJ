package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun NovedadesContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .padding(top = 45.dp)
    ) {

        Text(
            text = "Novedades y Reseñas",
            color = Color.White,
            fontSize = 22.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FiltroCategoria(
                texto = "Todo",
                seleccionado = true
            )

            FiltroCategoria(
                texto = "RPG"
            )

            FiltroCategoria(
                texto = "Acción"
            )

            FiltroCategoria(
                texto = "Aventura"
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        TarjetaJuego(
            imagen = R.drawable.logo_fortnite,
            etiqueta = "RESEÑA",
            nombre = "Cyber RPG 2077",
            descripcion = "Una obra maestra del género. Mundo abierto increíble con profundidad narrativa sin precedentes.",
            autor = "NeonPixel",
            calificacion = "4.5"
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        TarjetaJuego(
            imagen = R.drawable.logo_fortnite,
            etiqueta = "NOVEDAD",
            nombre = "Blockworld Adventure",
            descripcion = "Sandbox creativo con mecánicas de supervivencia. Horas y horas de diversión garantizada.",
            autor = "VoxelMaster",
            calificacion = "4.7"
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        TarjetaJuego(
            imagen = R.drawable.logo_fortnite,
            etiqueta = "CLÁSICO",
            nombre = "Uber RPG 2017",
            descripcion = "El mejor RPG de su generación. Historia épica con personajes memorables.",
            autor = "RetroGamer",
            calificacion = "4.2"
        )
    }
}