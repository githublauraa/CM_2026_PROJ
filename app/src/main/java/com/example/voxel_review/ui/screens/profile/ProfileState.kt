package com.example.voxel_review.ui.screens.profile

import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.data.info.review.ReviewInfo

enum class ProfileContentTab(val label: String) {
    REVIEWS("Reseñas"),
    LIBRARY("Colección")
}

data class ProfileState(
    val profiles: List<ProfileInfo> = emptyList(),
    val profile: ProfileInfo? = null,
    val reviews: List<ReviewInfo> = emptyList(),
    val isReviewsLoading: Boolean = true,
    val reviewsError: String? = null,
    val email: String ="",
    val profileImageUrl: String ? = null,
    val isReviewDeleted: Boolean = false,
    val isReviewUpdated: Boolean = false,
    val errorMessage: String? = null,
    val currentUserId: String = "",
    val isVerifyingEmail: Boolean = false,
    val verificationMessage: String? = null,
    val selectedTab: ProfileContentTab = ProfileContentTab.REVIEWS,
    val libraryGames: List<LibraryGameInfo> = emptyList(),
    val selectedLibraryFilter: LibraryStatus? = null,
    val isLibraryLoading: Boolean = false,
    val libraryError: String? = null
) {
    val filteredLibraryGames: List<LibraryGameInfo>
        get() = if (selectedLibraryFilter == null) {
            libraryGames
        } else {
            libraryGames.filter { it.status == selectedLibraryFilter }
        }
}
