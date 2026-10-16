package com.example.voxel_review.ui.screens.rankings

import com.example.voxel_review.data.info.profile.ProfileInfo

/**
 * Representa el estado de la interfaz de la pantalla de rankings.
 * Almacena la lista de usuarios y la opción de ranking seleccionada.
 *
 * @param rankingsUser Lista de usuarios que se muestran en el ranking.
 * @param seleccionado Indica si está seleccionado el ranking de críticos.
 */
data class RankingsState(
    val users: List<ProfileInfo> = emptyList(),
    val seleccionado: Boolean = true
)