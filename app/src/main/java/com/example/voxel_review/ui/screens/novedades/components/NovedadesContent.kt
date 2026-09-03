package com.example.voxel_review.ui.screens.novedades.components

import com.example.voxel_review.ui.screens.novedades.NovedadesState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.voxel_review.ui.screens.novedades.components.TarjetaJuego
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo

@Composable
fun NovedadesContent(
    state: NovedadesState,
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCategoriaSeleccionada: (String) -> Unit
) {
    val todo = "Todo"
    val rpg = "RPG"
    val accion = "Acción"
    val aventura = "Aventura"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .padding(top = 45.dp),
        contentPadding = PaddingValues(
            bottom = 70.dp
        )
    ) {

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.novedades_y_rese_as),
                    color = Color.White,
                    fontSize = 22.sp
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Campana(
                    modifier = Modifier.size(30.dp),
                    onClick = onNotificationClick
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FiltroCategoria(
                    texto = stringResource(R.string.todo),
                    seleccionado = state.categoriaSeleccionada == todo,
                    onClick = {
                        onCategoriaSeleccionada(todo)
                    }
                )

                FiltroCategoria(
                    texto = stringResource(R.string.rpg),
                    seleccionado = state.categoriaSeleccionada == rpg,
                    onClick = {
                        onCategoriaSeleccionada(rpg)
                    }
                )

                FiltroCategoria(
                    texto = stringResource(R.string.acci_n),
                    seleccionado = state.categoriaSeleccionada == accion,
                    onClick = {
                        onCategoriaSeleccionada(accion)
                    }
                )

                FiltroCategoria(
                    texto = stringResource(R.string.aventura),
                    seleccionado = state.categoriaSeleccionada == aventura,
                    onClick = {
                        onCategoriaSeleccionada(aventura)
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )
        }

        items(state.listaJuegos) { juego ->

            TarjetaJuego(
                imagen = juego.imagen,
                etiqueta = stringResource(juego.etiqueta),
                nombre = stringResource(juego.nombre),
                descripcion = stringResource(juego.descripcion),
                autor = stringResource(juego.autor),
                calificacion = stringResource(juego.calificacion),
                onClick = {
                    onClick(juego)
                }
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}

@Composable
@Preview
fun NovedadesContentPreview(){
    NovedadesContent(
        state = NovedadesState(),
        onCategoriaSeleccionada = {},
        onClick = {},
        onNotificationClick = {}
    )
}