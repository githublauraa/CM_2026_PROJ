package com.example.voxel_review.ui.screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.review.LocalReviewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState.asStateFlow()

    fun getGameReviews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = reviewRepository.getGameReviews("1")
            if (result.isSuccess) {
                _uiState.update { it.copy(reviews = result.getOrNull() ?: emptyList(),
                isLoading = false
                )
                }
            } else {
                _uiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message,
                        isLoading = false
                    )
                }

            }
        }
    }

    init {
        getGameReviews()
    }
}
