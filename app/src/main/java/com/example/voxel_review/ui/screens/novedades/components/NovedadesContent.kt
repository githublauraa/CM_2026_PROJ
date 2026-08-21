package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.data.LocalJuegosProvider

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.novedades_y_rese_as),
                color = Color.White,
                fontSize = 25.sp
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Campana()
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FiltroCategoria(
                texto = stringResource(R.string.todo)
            )

            FiltroCategoria(
                texto = stringResource(R.string.rpg)
            )

            FiltroCategoria(
                texto = stringResource(R.string.acci_n)
            )

            FiltroCategoria(
                texto = stringResource(R.string.aventura)
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )
        LocalJuegosProvider.juegos.forEach { juego ->
            TarjetaJuego(
                imagen = juego.imagen,
                etiqueta = stringResource(juego.etiqueta),
                nombre = stringResource(juego.nombre),
                descripcion = stringResource(juego.descripcion),
                autor = stringResource(juego.autor),
                calificacion = stringResource(juego.calificacion)
            )
            Spacer(
                modifier = Modifier.height(18.dp)
            )
        }
    }
}

@Composable
@Preview
fun NovedadesContentPreview(){
    NovedadesContent()
}