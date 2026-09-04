package com.example.voxel_review.ui.screens.review

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState

    fun loadReview() {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

    }
}