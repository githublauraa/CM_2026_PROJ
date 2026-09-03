package com.example.voxel_review.data.InfoGame

import com.example.voxel_review.data.InfoDiscover.GenreInfo

data class GameDetailInfo(
    val banner: Int,
    val nombre: String,
    val descripcion: String,
    val desarrollador: String,
    val lanzamiento: String,
    val generos: List<GenreInfo>,
    val imagen: Int

)
