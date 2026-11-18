package com.example.voxel_review.ui.screens.chat.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatInboxViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatInboxState())
    val uiState = _uiState.asStateFlow()

    private var conversationsJob: Job? = null
    private var requestedOlder = false

    init {
        loadConversations()
    }

    fun loadConversations() {
        conversationsJob?.cancel()
        conversationsJob = viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, error = null) }

            chatRepository.observeConversations().collect { result ->
                result.onSuccess { page ->
                    _uiState.update { state ->
                        state.copy(
                            conversations = chatRepository.mergeConversations(state.conversations, page.items),
                            nextCursor = if (requestedOlder) state.nextCursor else page.nextCursor,
                            isLoading = false,
                            error = null
                        )
                    }
                }.onFailure { error ->
                    _uiState.update { state -> state.copy(isLoading = false, error = error.message) }
                }
            }
        }
    }

    fun loadOlderConversations() {
        val state = _uiState.value
        val cursor = state.nextCursor ?: return
        if (state.isLoadingOlder) return

        requestedOlder = true
        _uiState.update { it.copy(isLoadingOlder = true, olderError = null) }

        viewModelScope.launch {
            chatRepository.loadOlderConversations(cursor)
                .onSuccess { page ->
                    _uiState.update { current ->
                        current.copy(
                            conversations = chatRepository.mergeConversations(page.items, current.conversations),
                            nextCursor = page.nextCursor,
                            isLoadingOlder = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoadingOlder = false, olderError = error.message) }
                }
        }
    }
}
