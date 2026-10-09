package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.dtos.VideoGameDto

interface VideoGameRemoteDataSource {

    suspend fun getAllVideoGames(): List<VideoGameDto>

    suspend fun getVideoGameById(id: Int): VideoGameDto
}