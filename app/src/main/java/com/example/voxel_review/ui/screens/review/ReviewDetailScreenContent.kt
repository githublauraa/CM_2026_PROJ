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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.review.components.DeleteReviewDialog
import com.example.voxel_review.ui.screens.review.components.EditReviewButton
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.utils.FondoPantalla
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReviewDetailScreenContent(
    state: ReviewDetailState,
    onClickReview: () -> Unit,
    onCommentClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onEditReview: (ReviewInfo) -> Unit,
    onDeleteReview: (ReviewInfo) -> Unit,
    onReviewProfileImageClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val review = state.reviews.firstOrNull()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    val canManageReview =
        !currentUserId.isNullOrBlank() &&
                review?.userId == currentUserId

    var showDeleteDialog by remember(review?.idResenia) {
        mutableStateOf(false)
    }

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
                showDelete = canManageReview,
                onBackClick = onBackClick,
                onDeleteClick = {
                    if (canManageReview) {
                        showDeleteDialog = true
                    }
                }
            )

            ReviewDetailContent(
                state = state,
                onClickReview = onClickReview,
                onCommentClick = onCommentClick,
                onReviewProfileImageClicked = onReviewProfileImageClicked
            )
        }

        if (review != null && canManageReview) {
            EditReviewButton(
                onClick = {
                    onEditReview(review)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(18.dp)
            )
        }

        if (showDeleteDialog && review != null && canManageReview) {
            DeleteReviewDialog(
                onConfirm = {
                    showDeleteDialog = false

                    val authenticatedUserId =
                        FirebaseAuth.getInstance().currentUser?.uid

                    if (
                        !authenticatedUserId.isNullOrBlank() &&
                        review.userId == authenticatedUserId
                    ) {
                        onDeleteReview(review)
                    }
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewDetailScreenContentPreview() {
    ReviewDetailScreenContent(
        state = ReviewDetailState(),
        onClickReview = {},
        onCommentClick = {},
        onBackClick = {},
        onEditReview = {},
        onDeleteReview = {}
    )
}