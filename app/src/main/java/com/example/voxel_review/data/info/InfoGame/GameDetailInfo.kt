package com.example.voxel_review.data.info.InfoGame

import com.example.voxel_review.data.info.InfoDiscover.GenreInfo

data class GameDetailInfo(
    val id: String,
    val banner: String,
    val nombre: String,
    val descripcion: String,
    val desarrollador: String,
    val lanzamiento: String,
    val generos: List<GenreInfo>,
    val imagen: String
)