package com.example.voxel_review.data.dataSource.impl

import com.example.voxel_review.data.dataSource.VideoGameRemoteDataSource
import com.example.voxel_review.data.dataSource.services.VideoGameRetrofitService
import com.example.voxel_review.data.dtos.VideoGameDto
import javax.inject.Inject

class VideoGameRetrofitDataSourceImpl @Inject constructor(
    private val videoGameService: VideoGameRetrofitService
) : VideoGameRemoteDataSource {

    override suspend fun getAllVideoGames(): List<VideoGameDto> {
        return videoGameService.getAllVideoGames()
    }

    override suspend fun getVideoGameById(id: Int): VideoGameDto {
        return videoGameService.getVideoGameById(id)
    }
}