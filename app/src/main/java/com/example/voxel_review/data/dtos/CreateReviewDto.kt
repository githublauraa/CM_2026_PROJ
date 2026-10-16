package com.example.voxel_review.data.dtos

data class CreateReviewDto(
    val userId: String,
    val videoGameId: String,
    val gameplayRating: Int,
    val graphicsRating: Int,
    val storyRating: Int,
    val content: String,

    val user: CreateReviewUserDto? = null,
    val videoGame: VideoGameDto? = null
)


data class CreateReviewUserDto(
    val username: String? = null,
    val photoUrl: String? = null,
)
