package com.example.voxel_review.ui.screens.GameDetail

import com.example.voxel_review.data.info.InfoGame.GameDetailInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.info.review.ReviewInfo

data class GameDetailState(
    val game: GameDetailInfo? = null,
    val allGames: List<GameDetailInfo> = emptyList(),
    val recommendedGames: List<GameDetailInfo> = emptyList(),
    val reviews: List<ReviewInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val reviewsError: String? = null,
    val currentUserId: String? = null,
    val isEmailVerified: Boolean = false,
    val verificationError: String? = null,
    val libraryStatus: LibraryStatus? = null,
    val isUpdatingLibrary: Boolean = false,
    val libraryError: String? = null,
    val updatingReactionIds: Set<String> = emptySet(),
    val reactionError: String? = null
)
