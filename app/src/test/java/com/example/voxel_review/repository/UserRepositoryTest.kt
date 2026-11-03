package com.example.voxel_review.repository


import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.voxel_review.data.dtos.UserProfileFirestoreDto
import com.example.voxel_review.data.repository.UserRepository
import io.mockk.mockk
import com.example.voxel_review.data.repository.AuthRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserRepositoryTest {

    private val mockDataSource = mockk<UserFirestoreDataSourceImpl>()
    private val authRepository = mockk<AuthRepository>()

    private val userRepository =  UserRepository(mockDataSource, authRepository)

    @Test
    fun `al llamar getUserById, si el id  usuario es valido retorna un result con el user info`() = runTest {
        //AAA
        val userDto = UserProfileFirestoreDto(
            userId = "123",
            username = "Juan",
            bio = "Me gustan los videojuegos y hacer reseñas.",
            photoUrl = "https://example.com/avatar.jpg",
            numReviews = 15,
            likes = 120,
            avgRating = 4.5f,
            followed = false,
            followersCount = 50,
            followingCount = 25
        )
        //Arrange
        coEvery { authRepository.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUserById("123", "1") } returns userDto
        //Act
        val result = userRepository.getUserById("123")
        //Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.nick).isEqualTo("Juan")
        Truth.assertThat(result.getOrNull()?.id).isEqualTo("123")
    }


}