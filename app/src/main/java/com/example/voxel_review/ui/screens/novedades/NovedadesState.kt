package com.example.voxel_review.ui.screens.novedades

import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo

data class NovedadesState(
    val listaJuegos: List<JuegoInfo> = emptyList(),
    val categoriaSeleccionada: String = "Todo"
)
