package com.example.voxel_review.ui.screens.GameDetail

import com.example.voxel_review.data.InfoGame.GameDetailInfo

/**
 * Representa el estado de la UI para la pantalla de detalle de juego.
 *
 * @property game Información detallada del juego seleccionado (null en estado de carga).
 * @property recommendedGames Lista de juegos recomendados.
 */
data class GameDetailState(

    val game: GameDetailInfo? = null,
    val allGames: List<GameDetailInfo> = emptyList(),
    val recommendedGames: List<GameDetailInfo> = emptyList()
)

