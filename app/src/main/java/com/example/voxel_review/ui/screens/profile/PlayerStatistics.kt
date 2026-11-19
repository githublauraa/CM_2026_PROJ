package com.example.voxel_review.ui.screens.profile

import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.info.review.ReviewInfo
import kotlin.math.roundToInt

data class PlayerStatistics(
    val reviewCount: Int,
    val averageRating: Double?,
    val libraryCounts: Map<LibraryStatus, Int>,
    val ratingCounts: List<Int>
)

/** Totals use the full collection, independently of the selected library filter. */
fun calculatePlayerStatistics(
    reviews: List<ReviewInfo>,
    library: List<LibraryGameInfo>
): PlayerStatistics {
    val validRatings = reviews.map { it.ratingGeneral }.filter { it.isFinite() && it in 0f..5f }
    return PlayerStatistics(
        reviewCount = reviews.size,
        averageRating = validRatings.takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average(),
        libraryCounts = LibraryStatus.entries.associateWith { status -> library.count { it.status == status } },
        ratingCounts = (0..5).map { bucket -> validRatings.count { it.roundToInt() == bucket } }
    )
}
