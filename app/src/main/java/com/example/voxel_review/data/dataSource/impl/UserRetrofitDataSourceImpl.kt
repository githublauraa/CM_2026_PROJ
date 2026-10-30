package com.example.voxel_review.data.dataSource.impl

import android.R
import com.example.voxel_review.data.dataSource.UserRemoteDataSource
import com.example.voxel_review.data.dataSource.services.UserRetrofitService
import javax.inject.Inject
import com.example.voxel_review.data.dtos.RegisterUserDto
import com.example.voxel_review.data.dtos.UserDtoGeneric
import com.example.voxel_review.data.dtos.UserProfileRetrofitDto

class UserRetrofitDataSourceImpl @Inject constructor(private val service: UserRetrofitService): UserRemoteDataSource {


    override suspend fun getUserById(
        id: String,
        currentUserId: String?
    ): UserDtoGeneric? {
        TODO("Not yet implemented")
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

    override suspend fun updateUsername(userdId: String, username: String) {
        TODO("Not yet implemented")
    }

    override suspend fun followOrUnfollow(
        currentUserId: String,
        targetUserId: String
    ) {
        TODO("Not yet implemented")
    }
}
