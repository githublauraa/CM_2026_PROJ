package com.example.voxel_review.ui.screens.profile.components

import com.example.voxel_review.data.review.ReviewInfo
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import com.example.voxel_review.ui.screens.profile.components.ProfileReviewCard


@Composable
fun ProfileReviews(
    reviews: List<ReviewInfo>,
    onEditReview: (String) -> Unit,
    onDeleteReview: (String) -> Unit
) {
    Column {
        reviews.forEach { review ->
            ProfileReviewCard(
                review = review,
                onEditClick = {
                    onEditReview(review.idResenia)
                },
                onDeleteClick = {
                    onDeleteReview(review.idResenia)
                }
            )
        }
    }
}
