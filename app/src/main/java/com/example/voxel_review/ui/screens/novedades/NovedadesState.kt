package com.example.voxel_review.ui.screens.novedades

import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.data.LocalJuegosProvider

data class NovedadesState(
    val listaJuegos: List<JuegoInfo> = LocalJuegosProvider.juegos,
    val categoriaSeleccionada: String = "Todo"
)
