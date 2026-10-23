package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.review.UserReviewsSection
import com.example.voxel_review.ui.theme.VoxelSurface
import kotlin.math.roundToInt

@Composable
fun FollowedReviewsSection(
    isLoading: Boolean,
    reviews: List<ReviewInfo>,
    onReviewClick: (ReviewInfo) -> Unit,
    onUserProfileClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (reviews.isEmpty()) {
        Text(
            text = "No hay publicaciones recientes de los usuarios que sigues.",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = modifier.padding(horizontal = 20.dp)
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            reviews.forEach { review ->
                UserReviewsSection(
                    imagenUsuario = review.imagenUsuario,
                    username = review.tituloDescripcion,
                    comentario = review.descripcion,
                    rating = review.ratingGeneral.roundToInt(),
                    cardBackgroundColor = VoxelSurface,
                    onClickReview = {
                        onReviewClick(review)
                    },
                    onCommentClick = {
                        onReviewClick(review)
                    },
                    onReviewProfileImageClicked = {
                        onUserProfileClick(review.userId)
                    }
                )
            }
        }
    }
}