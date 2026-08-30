package com.example.voxel_review.data.InfoDiscover

import com.example.voxel_review.R

object LocalDiscoverProvider {
    val generos = listOf(
        GenreInfo("1", R.string.genero_todo),
        GenreInfo("2", R.string.genero_rpg),
        GenreInfo("3", R.string.genero_accion),
        GenreInfo("4", R.string.genero_aventura),
        GenreInfo("5", R.string.genero_estrategia),
        GenreInfo("6", R.string.genero_indie)
    )

    val tendencias = listOf(
        TrendingSearchInfo(R.string.juego_elden_ring, R.string.cat_rpg_accion),
        TrendingSearchInfo(R.string.juego_starfield, R.string.cat_ciencia_ficcion),
        TrendingSearchInfo(R.string.juego_baldurs_gate_3, R.string.cat_rpg_turnos)
    )
}