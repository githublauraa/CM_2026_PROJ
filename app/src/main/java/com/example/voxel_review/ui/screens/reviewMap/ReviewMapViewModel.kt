package com.example.voxel_review.ui.screens.reviewMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewMapViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewMapState())
    val uiState: StateFlow<ReviewMapState> = _uiState.asStateFlow()

    init {
        loadReviews()
    }

    fun loadReviews() {
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            reviewRepository.getRecentReviewsWithLocation()
                .onSuccess { reviews ->
                    _uiState.update {
                        it.copy(reviews = reviews, isLoading = false)
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "No se pudieron cargar las reseñas"
                        )
                    }
                }
        }
    }
}
