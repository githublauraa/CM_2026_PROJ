package com.example.voxel_review.data.profile

import androidx.annotation.DrawableRes

data class Profile(
    @DrawableRes val pfp: Int,
    val nick: Int,
    val resenias: Int,
    val promedio: Float,
    val likes: Int,
)
