package com.example.voxel_review.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R
import com.example.voxel_review.data.info.review.ReviewInfo

@Composable
fun reviewTitle(review: ReviewInfo): String {
    val unknownUser = stringResource(R.string.generic_user)
    return stringResource(R.string.review_by, review.username.ifBlank { unknownUser })
}
