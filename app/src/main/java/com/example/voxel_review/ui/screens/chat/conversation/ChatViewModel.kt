package com.example.voxel_review.ui.screens.chat.conversation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.info.chat.CHAT_MESSAGE_LIMIT
import com.example.voxel_review.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val contactId = savedStateHandle.get<String>("contactId").orEmpty()
    private val _uiState = MutableStateFlow(
        ChatState(
            draft = savedStateHandle.get<String>("draft").orEmpty(),
            photoDraft = savedStateHandle.get<Uri>("photoDraft")
        )
    )
    val uiState = _uiState.asStateFlow()

    private var chatJob: Job? = null
    private var requestedOlder = false

    init {
        loadChat()
    }

    fun loadChat() {
        chatJob?.cancel()
        chatJob = viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, contactError = null, messagesError = null) }

            launch {
                chatRepository.getContact(contactId)
                    .onSuccess { contact -> _uiState.update { it.copy(contact = contact) } }
                    .onFailure { error -> _uiState.update { it.copy(contactError = error.message) } }
            }

            chatRepository.observeMessages(contactId).collect { result ->
                result.onSuccess { page ->
                    _uiState.update { state ->
                        state.copy(
                            messages = chatRepository.mergeMessages(state.messages, page.items),
                            nextCursor = if (requestedOlder) state.nextCursor else page.nextCursor,
                            isLoading = false,
                            messagesError = null
                        )
                    }
                }.onFailure { error ->
                    _uiState.update { state -> state.copy(isLoading = false, messagesError = error.message) }
                }
            }
        }
    }

    fun loadOlderMessages() {
        val state = _uiState.value
        val cursor = state.nextCursor ?: return
        if (state.isLoadingOlder) return

        requestedOlder = true
        _uiState.update { it.copy(isLoadingOlder = true, olderError = null) }

        viewModelScope.launch {
            chatRepository.loadOlderMessages(contactId, cursor)
                .onSuccess { page ->
                    _uiState.update { current ->
                        current.copy(
                            messages = chatRepository.mergeMessages(page.items, current.messages),
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

    fun updateDraft(text: String) {
        if (_uiState.value.isSending || text.length > CHAT_MESSAGE_LIMIT) return
        savedStateHandle["draft"] = text
        _uiState.update { state -> state.copy(draft = text, sendError = null) }
    }

    fun sendMessage() {
        val state = _uiState.value
        if (state.draft.isBlank() || state.contact == null || state.isSending) return

        val message = state.draft
        savedStateHandle["draft"] = ""
        _uiState.update { it.copy(draft = "", isSending = true, sendError = null) }

        viewModelScope.launch {
            chatRepository.sendMessage(contactId, message)
                .onSuccess {
                    _uiState.update { it.copy(isSending = false) }
                }
                .onFailure { error ->
                    savedStateHandle["draft"] = message
                    _uiState.update { it.copy(draft = message, isSending = false, sendError = error.message) }
                }
        }
    }
    fun onPhotoCaptured(capture: Uri) {
        savedStateHandle["photoDraft"] = capture
        _uiState.update { it.copy(photoDraft = capture, sendError = null) }
    }

    fun onCameraError(message: String) {
        _uiState.update { it.copy(sendError = message) }
    }

    fun discardPhoto() {
        val state = _uiState.value
        val capture = state.photoDraft ?: return
        if (state.isSending) return
        clearPhoto(capture)
    }

    fun sendPhoto() {
        val state = _uiState.value
        val capture = state.photoDraft ?: return
        if (state.contact == null || state.isSending) return

        savedStateHandle.remove<Uri>("photoDraft")
        _uiState.update { it.copy(photoDraft = null, isSending = true, sendError = null) }
        viewModelScope.launch {
            chatRepository.sendPhoto(contactId, capture)
                .onSuccess { clearPhoto(capture) }
                .onFailure { error ->
                    savedStateHandle["photoDraft"] = capture
                    _uiState.update { it.copy(photoDraft = capture, sendError = error.message) }
                }
            _uiState.update { it.copy(isSending = false) }
        }
    }

    suspend fun loadPhoto(path: String): Result<Bitmap> =
        chatRepository.loadPhoto(contactId, path)

    private fun clearPhoto(capture: Uri) {
        val cleanup = chatRepository.discardCapture(capture)
        savedStateHandle.remove<Uri>("photoDraft")
        _uiState.update {
            it.copy(photoDraft = null, sendError = cleanup.exceptionOrNull()?.message)
        }
    }

}
