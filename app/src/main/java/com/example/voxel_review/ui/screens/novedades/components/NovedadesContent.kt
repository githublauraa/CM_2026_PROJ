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

        TarjetaJuego(
            imagen = R.drawable.logo_fortnite,
            etiqueta = stringResource(R.string.rese_a),
            nombre = stringResource(R.string.cyber_rpg_2077),
            descripcion = stringResource(R.string.una_obra_maestra_del_g_nero_mundo_abierto_incre_ble_con_profundidad_narrativa_sin_precedentes),
            autor = stringResource(R.string.neonpixel),
            calificacion = stringResource(R.string._4_5)
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        TarjetaJuego(
            imagen = R.drawable.logo_fortnite,
            etiqueta = stringResource(R.string.novedad),
            nombre = stringResource(R.string.blockworld_adventure),
            descripcion = stringResource(R.string.sandbox_creativo_con_mec_nicas_de_supervivencia_horas_y_horas_de_diversi_n_garantizada),
            autor = stringResource(R.string.voxelmaster),
            calificacion = stringResource(R.string._4_7)
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        TarjetaJuego(
            imagen = R.drawable.logo_fortnite,
            etiqueta = stringResource(R.string.cl_sico),
            nombre = stringResource(R.string.uber_rpg_2017),
            descripcion = stringResource(R.string.el_mejor_rpg_de_su_generaci_n_historia_pica_con_personajes_memorables),
            autor = stringResource(R.string.retrogamer),
            calificacion = stringResource(R.string._4_2)
        )
    }
}

@Composable
@Preview
fun NovedadesContentPreview(){
    NovedadesContent()
}