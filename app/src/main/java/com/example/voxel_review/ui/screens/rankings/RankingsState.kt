package com.example.voxel_review.ui.screens.rankings

import com.example.voxel_review.data.infoRanking.RankingUsuario
import com.example.voxel_review.data.infoRanking.ListaRanking

data class RankingsState(
    val rankingsUser: List<RankingUsuario> = ListaRanking.rankingUsuarios,
    val seleccionado: Boolean = true
)
