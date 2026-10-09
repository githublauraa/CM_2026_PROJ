package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.review.ReviewInfo
import com.example.voxel_review.ui.screens.review.components.DeleteReviewDialog
import com.example.voxel_review.ui.screens.review.components.EditReviewButton
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ReviewDetailScreenContent(
    state: ReviewDetailState,
    onClickReview: () -> Unit,
    onCommentClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onEditReview: (ReviewInfo) -> Unit,
    onDeleteReview: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    val review = state.reviews.firstOrNull()
    var showDeleteDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {

            ReviewDetailTopBar(
                onBackClick = onBackClick,
                onDeleteClick = { showDeleteDialog = true }
            )

            ReviewDetailContent(
                state = state,
                onClickReview = onClickReview,
                onCommentClick = onCommentClick
            )
        }

        if (review != null) {
            EditReviewButton(
                onClick = { onEditReview(review) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(18.dp)
            )
        }

        if (showDeleteDialog && review != null) {
            DeleteReviewDialog(
                onConfirm = {
                    showDeleteDialog = false
                    onDeleteReview(review)
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }
    }
}
