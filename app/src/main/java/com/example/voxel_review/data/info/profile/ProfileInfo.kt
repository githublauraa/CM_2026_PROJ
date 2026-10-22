package com.example.voxel_review.data.info.profile

data class ProfileInfo(
    val pfp: String,
    val id: String,
    val nick: String,
    val numResenias: Int,
    val promedio: Float,
    val likes: Int,
    val biografia: String,
    val followed: Boolean = false,
    val followers : Int = 0,
    val following: Int = 0,
)
