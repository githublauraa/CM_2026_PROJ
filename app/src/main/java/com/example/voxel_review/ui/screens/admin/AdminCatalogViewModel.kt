package com.example.voxel_review.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.R
import com.example.voxel_review.data.dtos.VideoGameDto
import com.example.voxel_review.data.repository.AdminAccessRepository
import com.example.voxel_review.data.repository.AdminCatalogRepository
import com.example.voxel_review.data.repository.DuplicateGameException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminCatalogState(
    val checkingAccess: Boolean = true,
    val isAdmin: Boolean = false,
    val busy: Boolean = false,
    val games: List<VideoGameDto> = emptyList(),
    val draft: GameDraft? = null,
    val message: Int? = null
)

@HiltViewModel
class AdminCatalogViewModel @Inject constructor(
    private val access: AdminAccessRepository,
    private val catalog: AdminCatalogRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AdminCatalogState())
    val state = _state.asStateFlow()

    init { refresh() }

    fun refresh() {
        if (_state.value.busy) return
        _state.update { it.copy(checkingAccess = true, isAdmin = false, busy = true, message = null) }
        viewModelScope.launch {
            try {
                val allowed = access.isAdmin()
                val games = if (allowed) catalog.loadGames() else emptyList()
                _state.update { it.copy(isAdmin = allowed, games = games) }
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                _state.update { it.copy(message = R.string.admin_load_error) }
            } finally {
                _state.update { it.copy(checkingAccess = false, busy = false) }
            }
        }
    }

    fun edit(game: VideoGameDto? = null) {
        if (_state.value.isAdmin && !_state.value.busy)
            _state.update { it.copy(draft = game?.let(GameDraft::from) ?: GameDraft(), message = null) }
    }

    fun updateDraft(draft: GameDraft) { if (!_state.value.busy) _state.update { it.copy(draft = draft, message = null) } }
    fun cancelEdit() { if (!_state.value.busy) _state.update { it.copy(draft = null, message = null) } }

    fun save() {
        val draft = _state.value.draft ?: return
        if (!draft.isValid()) { _state.update { it.copy(message = R.string.admin_invalid) }; return }
        mutate {
            catalog.save(draft.toGame())
            _state.update { it.copy(draft = null) }
        }
    }

    fun archive(game: VideoGameDto) = mutate { catalog.setArchived(game.videoGameId, !game.archived) }

    private fun mutate(action: suspend () -> Unit) {
        if (!_state.value.isAdmin || _state.value.busy) return
        _state.update { it.copy(busy = true, message = null) }
        viewModelScope.launch {
            try {
                action()
                _state.update { it.copy(games = catalog.loadGames(), message = R.string.admin_saved) }
            } catch (e: CancellationException) { throw e
            } catch (e: DuplicateGameException) {
                _state.update { it.copy(message = R.string.admin_duplicate) }
            } catch (e: Exception) {
                _state.update { it.copy(message = R.string.admin_save_error) }
            } finally { _state.update { it.copy(busy = false) } }
        }
    }
}
