package com.example.voxel_review.data.dtos

data class RegisterUserDto(
    val username: String,
    val bio: String? = null,
    val photoUrl: String? = null,
    val FCMToken: String
)
