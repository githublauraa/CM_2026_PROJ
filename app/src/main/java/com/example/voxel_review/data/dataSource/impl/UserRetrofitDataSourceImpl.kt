package com.example.voxel_review.data.dataSource.impl

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dataSource.services.UserRetrofitService
import com.example.voxel_review.data.dtos.UserProfileDto
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
class UserRetrofitDataSourceImpl @Inject constructor(private val service: UserRetrofitService): UserRemoteDataSource {

    override suspend fun getUserById(id: String): UserProfileDto {
	    return service.getUserById(id)
    }

    override suspend fun getAllUsers(): List<UserProfileDto> {
        return service.getAllUsers()
    }


    override suspend fun registerUser(registerUser: RegisterUserDto, id: String): Unit {
        //
    }
}
