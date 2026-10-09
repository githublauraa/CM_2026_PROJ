package com.example.voxel_review.data.dataSource.services

import com.example.voxel_review.data.dtos.VideoGameDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VideoGameRetrofitService {

    @GET("video-games")
    suspend fun getAllVideoGames(): List<VideoGameDto>

    @GET("video-games/{id}")
    suspend fun getVideoGameById(
        @Path("id") id: Int
    ): VideoGameDto
}