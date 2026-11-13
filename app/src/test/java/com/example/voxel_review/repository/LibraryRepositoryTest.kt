package com.example.voxel_review.repository

import com.example.voxel_review.data.dataSource.impl.firestore.LibraryFirestoreDataSourceImpl
import com.example.voxel_review.data.dtos.LibraryEntryDto
import com.example.voxel_review.data.info.InfoGame.GameDetailInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.repository.LibraryRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LibraryRepositoryTest {

    private val dataSource = mockk<LibraryFirestoreDataSourceImpl>()
    private val repository = LibraryRepository(dataSource)

    private val sampleGame = GameDetailInfo(
        id = "game-1",
        banner = "https://example.com/banner.jpg",
        nombre = "Hollow Knight",
        descripcion = "Metroidvania",
        desarrollador = "Team Cherry",
        lanzamiento = "2017",
        generos = emptyList(),
        imagen = "https://example.com/cover.jpg"
    )

    @Test
    fun `getUserLibrary mapea correctamente los estados validos desde el datasource`() = runTest {
        coEvery { dataSource.getUserLibrary("user-1") } returns listOf(
            LibraryEntryDto(
                videoGameId = "game-1",
                gameName = "Hollow Knight",
                gameImageUrl = "https://example.com/cover.jpg",
                gameDeveloper = "Team Cherry",
                status = LibraryStatus.PLAYING.name,
                updatedAt = 200L
            ),
            LibraryEntryDto(
                videoGameId = "game-2",
                gameName = "Invalid Status Game",
                status = "UNKNOWN_STATUS",
                updatedAt = 100L
            )
        )

        val result = repository.getUserLibrary("user-1")

        assertThat(result.isSuccess).isTrue()
        val list = result.getOrNull()!!
        assertThat(list).hasSize(1)
        assertThat(list.first().videoGameId).isEqualTo("game-1")
        assertThat(list.first().status).isEqualTo(LibraryStatus.PLAYING)
    }

    @Test
    fun `getGameStatus retorna el estado actual o null si no existe`() = runTest {
        coEvery { dataSource.getGameEntry("user-1", "game-1") } returns LibraryEntryDto(
            videoGameId = "game-1",
            status = LibraryStatus.COMPLETED.name
        )
        coEvery { dataSource.getGameEntry("user-1", "game-2") } returns null

        val found = repository.getGameStatus("user-1", "game-1")
        val missing = repository.getGameStatus("user-1", "game-2")

        assertThat(found.getOrNull()).isEqualTo(LibraryStatus.COMPLETED)
        assertThat(missing.getOrNull()).isNull()
    }

    @Test
    fun `setGameStatus guarda en datasource y retorna success`() = runTest {
        coEvery { dataSource.upsertLibraryEntry("user-1", any()) } returns Unit

        val result = repository.setGameStatus("user-1", sampleGame, LibraryStatus.WANT_TO_PLAY)

        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) {
            dataSource.upsertLibraryEntry(
                "user-1",
                match {
                    it.videoGameId == "game-1" &&
                        it.gameName == "Hollow Knight" &&
                        it.status == LibraryStatus.WANT_TO_PLAY.name
                }
            )
        }
    }

    @Test
    fun `removeGameFromLibrary elimina en datasource y propaga fallo si ocurre excepcion`() = runTest {
        coEvery { dataSource.deleteLibraryEntry("user-1", "game-1") } returns Unit
        coEvery { dataSource.deleteLibraryEntry("user-1", "game-err") } throws RuntimeException("Error de red")

        val ok = repository.removeGameFromLibrary("user-1", "game-1")
        val fail = repository.removeGameFromLibrary("user-1", "game-err")

        assertThat(ok.isSuccess).isTrue()
        assertThat(fail.isFailure).isTrue()
        assertThat(fail.exceptionOrNull()?.message).isEqualTo("Error de red")
    }
}
