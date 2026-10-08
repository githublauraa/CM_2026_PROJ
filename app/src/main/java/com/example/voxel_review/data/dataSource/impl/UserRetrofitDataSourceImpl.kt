package com.example.voxel_review.data.dataSource.impl

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dataSource.services.UserRetrofitService
import com.example.voxel_review.data.dtos.UserProfileDto
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(private val service: UserRetrofitService): UserRemoteDataSource {

    override suspend fun getUserById(id: String): UserProfileDto {
	    return service.getUserById(id)
    }

}
