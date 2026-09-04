package com.example.voxel_review.ui.screens.rankings

import com.example.voxel_review.data.infoRanking.ListaRanking
import com.example.voxel_review.data.infoRanking.RankingUsuario

/**
 * Representa el estado de la interfaz de la pantalla de rankings.
 * Almacena la lista de usuarios y la opción de ranking seleccionada.
 *
 * @param rankingsUser Lista de usuarios que se muestran en el ranking.
 * @param seleccionado Indica si está seleccionado el ranking de críticos.
 */
data class RankingsState(
    val rankingsUser: List<RankingUsuario> = ListaRanking.rankingUsuarios,
    val seleccionado: Boolean = true
)