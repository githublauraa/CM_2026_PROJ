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

    @Test
    fun `al llamar getUserById, si el id del usuario no es valido debe retorna un Result failure`() = runTest {
        //AAA
        //Arange
        coEvery { authRepository.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUserById("999", "1") } returns null
        //Act
        val result = userRepository.getUserById("999")
        //Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("User not found")
    }

    @Test
    fun `al llamar getUserById, si el dataSource devuelve campos vacios se deben mapear correctamente`() = runTest {
        //AAA
        //AAA
        val userDto = UserProfileFirestoreDto(
            userId = "123",
            username = "",
            bio = null,
            photoUrl = null,
            numReviews = 0,
            likes = 0,
            avgRating = 0f,
            followed = false,
            followersCount = 0,
            followingCount = 0
        )
        //Arange
        coEvery { authRepository.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUserById("123", "1") } returns userDto
        //Act
        val result = userRepository.getUserById("123")
        //Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.id).isEqualTo("123")
        Truth.assertThat(result.getOrNull()?.biografia).isEqualTo("No hay biografia")
        Truth.assertThat(result.getOrNull()?.pfp).isEqualTo("")
    }

    @Test
    fun `al llamar getAllUsers, si el datasource devuelve un listado de usuarios, se deben traer todos los usuarios`() = runTest {
        val usersDto = listOf(
            UserProfileFirestoreDto(
                userId = "123",
                username = "Juan",
                bio = "Me gustan los videojuegos y hacer reseñas.",
                photoUrl = "https://example.com/avatar1.jpg",
                numReviews = 15,
                likes = 120,
                avgRating = 4.5f,
                followed = false,
                followersCount = 50,
                followingCount = 25
            ),
            UserProfileFirestoreDto(
                userId = "456",
                username = "Carlos",
                bio = "Fanático de los juegos de aventura y acción.",
                photoUrl = "https://example.com/avatar2.jpg",
                numReviews = 8,
                likes = 65,
                avgRating = 3.8f,
                followed = true,
                followersCount = 30,
                followingCount = 15
            ),
            UserProfileFirestoreDto(
                userId = "789",
                username = "Maria",
                bio = "Me encantan los juegos de rol y estrategia.",
                photoUrl = "https://example.com/avatar3.jpg",
                numReviews = 22,
                likes = 200,
                avgRating = 4.9f,
                followed = false,
                followersCount = 100,
                followingCount = 45
            )
        )

        //AAA
        //Arange
        coEvery { mockDataSource.getAllUsers() } returns usersDto
        //Act
        val result = userRepository.getAllUsers()
        //Assert
        val usuarios = result.getOrThrow()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.size).isEqualTo(usersDto.size)
        //verificar ids
        Truth.assertThat(usuarios[0].id).isEqualTo(usersDto[0].userId)
        Truth.assertThat(usuarios[1].id).isEqualTo(usersDto[1].userId)
        Truth.assertThat(usuarios[2].id).isEqualTo(usersDto[2].userId)
        //verificar nombres
        Truth.assertThat(usuarios[0].nick).isEqualTo(usersDto[0].username)
        Truth.assertThat(usuarios[1].nick).isEqualTo(usersDto[1].username)
        Truth.assertThat(usuarios[2].nick).isEqualTo(usersDto[2].username)

    }

    @Test
    fun `al llamar getFollowing, si el datasource devuelve un listado de usuarios, se deben traer todos los usuarios`() = runTest {
        //AAA
        val usersDto = listOf(
            UserProfileFirestoreDto(
                userId = "123",
                username = "Juan",
                bio = "Me gustan los videojuegos y hacer reseñas.",
                photoUrl = "https://example.com/avatar1.jpg",
                numReviews = 15,
                likes = 120,
                avgRating = 4.5f,
                followed = false,
                followersCount = 50,
                followingCount = 25
            ),
            UserProfileFirestoreDto(
                userId = "456",
                username = "Carlos",
                bio = "Fanático de los juegos de aventura y acción.",
                photoUrl = "https://example.com/avatar2.jpg",
                numReviews = 8,
                likes = 65,
                avgRating = 3.8f,
                followed = true,
                followersCount = 30,
                followingCount = 15
            ),
            UserProfileFirestoreDto(
                userId = "789",
                username = "Maria",
                bio = "Me encantan los juegos de rol y estrategia.",
                photoUrl = "https://example.com/avatar3.jpg",
                numReviews = 22,
                likes = 200,
                avgRating = 4.9f,
                followed = false,
                followersCount = 100,
                followingCount = 45
            )
        )
        // Arrange
        coEvery { mockDataSource.getFollowing("123") } returns usersDto
        //Act
        val result = userRepository.getFollowing("123")
        //Assert
        val usuarios = result.getOrThrow()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.size).isEqualTo(usersDto.size)
        //verificar los ids
        Truth.assertThat(usuarios[0].id).isEqualTo(usersDto[0].userId)
        Truth.assertThat(usuarios[1].id).isEqualTo(usersDto[1].userId)
        Truth.assertThat(usuarios[2].id).isEqualTo(usersDto[2].userId)
        //verificar nombres
        Truth.assertThat(usuarios[0].nick).isEqualTo(usersDto[0].username)
        Truth.assertThat(usuarios[1].nick).isEqualTo(usersDto[1].username)
        Truth.assertThat(usuarios[2].nick).isEqualTo(usersDto[2].username)
    }

    @Test
    fun `al llamar getFollowers, si el datasource devuelve un listado de usuarios, se deben traer todos los usuarios`() = runTest {
        //AAA
        val usersDto = listOf(
            UserProfileFirestoreDto(
                userId = "123",
                username = "Juan",
                bio = "Me gustan los videojuegos y hacer reseñas.",
                photoUrl = "https://example.com/avatar1.jpg",
                numReviews = 15,
                likes = 120,
                avgRating = 4.5f,
                followed = false,
                followersCount = 50,
                followingCount = 25
            ),
            UserProfileFirestoreDto(
                userId = "456",
                username = "Carlos",
                bio = "Fanático de los juegos de aventura y acción.",
                photoUrl = "https://example.com/avatar2.jpg",
                numReviews = 8,
                likes = 65,
                avgRating = 3.8f,
                followed = true,
                followersCount = 30,
                followingCount = 15
            ),
            UserProfileFirestoreDto(
                userId = "789",
                username = "Maria",
                bio = "Me encantan los juegos de rol y estrategia.",
                photoUrl = "https://example.com/avatar3.jpg",
                numReviews = 22,
                likes = 200,
                avgRating = 4.9f,
                followed = false,
                followersCount = 100,
                followingCount = 45
            )
        )
        // Arrange
        coEvery { mockDataSource.getFollowers("123") } returns usersDto
        //Act
        val result = userRepository.getFollowers("123")
        //Assert
        val usuarios = result.getOrThrow()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.size).isEqualTo(usersDto.size)
        //verificar los ids
        Truth.assertThat(usuarios[0].id).isEqualTo(usersDto[0].userId)
        Truth.assertThat(usuarios[1].id).isEqualTo(usersDto[1].userId)
        Truth.assertThat(usuarios[2].id).isEqualTo(usersDto[2].userId)
        //verificar nombres
        Truth.assertThat(usuarios[0].nick).isEqualTo(usersDto[0].username)
        Truth.assertThat(usuarios[1].nick).isEqualTo(usersDto[1].username)
        Truth.assertThat(usuarios[2].nick).isEqualTo(usersDto[2].username)
    }

}