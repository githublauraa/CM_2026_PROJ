package com.example.voxel_review.data

import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.R

object LocalJuegosProvider {

    val juegos = listOf(

        JuegoInfo(
            imagen = R.drawable.logo_fortnite,
            etiqueta = R.string.rese_a,
            nombre = R.string.cyber_rpg_2077,
            descripcion = R.string.una_obra_maestra_del_g_nero_mundo_abierto_incre_ble_con_profundidad_narrativa_sin_precedentes,
            autor = R.string.neonpixel,
            calificacion = R.string._4_5
        ),

        JuegoInfo(
            imagen = R.drawable.logo_fortnite,
            etiqueta = R.string.novedad,
            nombre = R.string.blockworld_adventure,
            descripcion = R.string.sandbox_creativo_con_mec_nicas_de_supervivencia_horas_y_horas_de_diversi_n_garantizada,
            autor = R.string.voxelmaster,
            calificacion = R.string._4_7
        ),

        JuegoInfo(
            imagen = R.drawable.logo_fortnite,
            etiqueta = R.string.cl_sico,
            nombre = R.string.uber_rpg_2017,
            descripcion = R.string.el_mejor_rpg_de_su_generaci_n_historia_pica_con_personajes_memorables,
            autor = R.string.retrogamer,
            calificacion = R.string._4_2
        )
    )
}