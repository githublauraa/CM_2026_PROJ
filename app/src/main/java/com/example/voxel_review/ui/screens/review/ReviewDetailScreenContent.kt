package com.example.voxel_review.ui.screens.review

import com.example.voxel_review.R
import android.content.Intent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
    modifier: Modifier = Modifier,
    onReactionClick: (String, Boolean) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val review = state.review
    val shareLocale = LocalConfiguration.current.locales[0]
    val genericUser = stringResource(R.string.generic_user)
    val shareAuthor = stringResource(R.string.review_by, review?.username?.ifBlank { genericUser } ?: genericUser)
    val shareScores = stringResource(
        R.string.share_review_scores,
        review?.ratingJugabilidad?.toInt() ?: 0,
        review?.ratingGraficos?.toInt() ?: 0,
        review?.ratingHistoria?.toInt() ?: 0
    )
    val spoilerWarning = stringResource(R.string.review_spoiler_warning)
    val sharedFrom = stringResource(R.string.shared_from)
    val shareSubject = stringResource(R.string.share_review_subject, review?.tituloJuego.orEmpty())
    val shareChooser = stringResource(R.string.share_review)

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
                },
                onShareClick = {
                    if (review != null) {
                        val ratingFormatted = String.format(shareLocale, "%.1f", review.ratingGeneral)
                        val shareText = buildString {
                            appendLine("🎮 ${review.tituloJuego} ($ratingFormatted/5 ⭐)")
                            appendLine("✍️🔥 $shareAuthor")
                            appendLine(shareScores)
                            appendLine()
                            if (review.containsSpoilers) {
                                appendLine(spoilerWarning)
                            } else {
                                appendLine("\"${review.descripcion}\"")
                            }
                            appendLine()
                            append(sharedFrom)
                        }
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_SUBJECT,
                                shareSubject
                            )
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(
                            Intent.createChooser(sendIntent, shareChooser)
                        )
                    }
                }
            )

            ReviewDetailContent(
                state = state,
                onClickReview = onClickReview,
                onCommentClick = onCommentClick,
                onReviewProfileImageClicked = onReviewProfileImageClicked,
                onReactionClick = onReactionClick
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
