package com.example.voxel_review.admin

import com.example.voxel_review.R
import com.example.voxel_review.data.dtos.VideoGameDto
import com.example.voxel_review.data.repository.AdminAccessRepository
import com.example.voxel_review.data.repository.AdminCatalogRepository
import com.example.voxel_review.data.repository.DuplicateGameException
import com.example.voxel_review.ui.screens.admin.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class AdminCatalogViewModelTest {
    private val access = mockk<AdminAccessRepository>()
    private val catalog = mockk<AdminCatalogRepository>()
    private val game = VideoGameDto("game", "Celeste", "Plataformas", "Maddy", "PC", "2018-01-25", "https://example.com/a.jpg", listOf("Indie"))
    @Before fun setup() { Dispatchers.setMain(StandardTestDispatcher()) }
    @After fun cleanup() { Dispatchers.resetMain() }

    @Test fun `normal account cannot load or mutate catalog`() = runTest {
        coEvery { access.isAdmin() } returns false
        val vm = AdminCatalogViewModel(access, catalog)
        advanceUntilIdle()
        vm.edit(); vm.save(); vm.archive(game)
        advanceUntilIdle()
        assertFalse(vm.state.value.isAdmin)
        assertNull(vm.state.value.draft)
        coVerify(exactly = 0) { catalog.loadGames() }
        coVerify(exactly = 0) { catalog.save(any()) }
        coVerify(exactly = 0) { catalog.setArchived(any(), any()) }
    }
    @Test fun `admin saves and reloads catalog`() = runTest {
        coEvery { access.isAdmin() } returns true
        coEvery { catalog.loadGames() } returns listOf(game)
        coEvery { catalog.save(any()) } returns "game"
        val vm = AdminCatalogViewModel(access, catalog)
        advanceUntilIdle()
        vm.edit(game); vm.save(); advanceUntilIdle()
        assertNull(vm.state.value.draft)
        assertFalse(vm.state.value.busy)
        assertEquals(R.string.admin_saved, vm.state.value.message)
        coVerify(exactly = 1) { catalog.save(game) }
    }
    @Test fun `duplicate and failed writes preserve the draft for retry`() = runTest {
        coEvery { access.isAdmin() } returns true
        coEvery { catalog.loadGames() } returns listOf(game)
        coEvery { catalog.save(any()) } throws DuplicateGameException()
        val vm = AdminCatalogViewModel(access, catalog)
        advanceUntilIdle(); vm.edit(game); vm.save(); advanceUntilIdle()
        assertEquals(R.string.admin_duplicate, vm.state.value.message)
        assertNotNull(vm.state.value.draft)
        coEvery { catalog.save(any()) } throws IllegalStateException("permission-denied")
        vm.save(); advanceUntilIdle()
        assertEquals(R.string.admin_save_error, vm.state.value.message)
        assertNotNull(vm.state.value.draft)
    }
    @Test fun `archive keeps game and restore reverses archived status`() = runTest {
        coEvery { access.isAdmin() } returns true
        coEvery { catalog.loadGames() } returns listOf(game.copy(archived = true))
        coEvery { catalog.setArchived(any(), any()) } just Runs
        val vm = AdminCatalogViewModel(access, catalog)
        advanceUntilIdle(); vm.archive(game); advanceUntilIdle()
        assertEquals(1, vm.state.value.games.size)
        vm.archive(game.copy(archived = true)); advanceUntilIdle()
        coVerify { catalog.setArchived("game", true) }
        coVerify { catalog.setArchived("game", false) }
    }
}
