package com.example.voxel_review.ui.screens.novedades

import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.data.LocalJuegosProvider

/**
 * Representa el estado de la interfaz de la pantalla de novedades.
 * Almacena la lista de juegos que se muestra y la categoría seleccionada.
 *
 * @param listaJuegos Lista de juegos disponibles para mostrar en la pantalla.
 * @param categoriaSeleccionada Categoría actualmente seleccionada por el usuario.
 */
data class NovedadesState(
    val listaJuegos: List<JuegoInfo> = LocalJuegosProvider.juegos,
    val categoriaSeleccionada: String = "Todo"
)