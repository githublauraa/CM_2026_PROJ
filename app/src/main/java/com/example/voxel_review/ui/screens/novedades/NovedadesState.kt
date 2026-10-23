package com.example.voxel_review.ui.screens.novedades

import com.example.voxel_review.data.info.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.data.info.review.ReviewInfo

data class NovedadesState(
    val listaJuegos: List<JuegoInfo> = emptyList(),
    val categoriaSeleccionada: String = "Todo",
    val isLoading: Boolean = false,
    val error: String? = null,
    val followedReviews: List<ReviewInfo> = emptyList(),
    val showOnlyFollowed: Boolean = false
)