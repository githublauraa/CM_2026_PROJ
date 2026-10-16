package com.example.voxel_review.data.dataSource.impl

import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dataSource.services.UserRetrofitService
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserDtoGeneric
import com.example.voxel_review.data.dtos.UserProfileRetrofitDto

class UserRetrofitDataSourceImpl @Inject constructor(private val service: UserRetrofitService): UserRemoteDataSource {

    override suspend fun getUserById(id: String): UserProfileRetrofitDto {
	    return service.getUserById(id)
    }

    override suspend fun getAllUsers(): List<UserProfileRetrofitDto> {
        return service.getAllUsers()
    }


    override suspend fun registerUser(registerUser: RegisterUserDto, id: String): Unit {
        //
    }

    override suspend fun updateUserBio(userId: String, bio: String) {
        //
    }
}
