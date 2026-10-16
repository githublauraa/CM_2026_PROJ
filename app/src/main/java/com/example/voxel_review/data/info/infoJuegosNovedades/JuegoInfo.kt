package com.example.voxel_review.data.info.infoJuegosNovedades

data class JuegoInfo(
    val id: String,
    val imagen: String,
    val etiqueta: String,
    val nombre: String,
    val descripcion: String,
    val autor: String,
    val calificacion: Float?
)