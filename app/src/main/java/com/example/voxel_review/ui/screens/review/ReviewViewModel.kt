package com.example.voxel_review.ui.screens.review

import androidx.lifecycle.ViewModel
import com.example.voxel_review.ui.screens.Discover.DiscoverState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState

    fun loadReview() {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

    }
}