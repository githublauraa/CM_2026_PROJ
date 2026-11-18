package com.example.voxel_review.ui.screens.GameDetail.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp

@Composable
fun ReviewReactions(
    isLiked: Boolean,
    likes: Int,
    isDisliked: Boolean,
    dislikes: Int,
    enabled: Boolean = true,
    onReactionClick: (dislike: Boolean) -> Unit
) {
    Row {
        ReactionButton(
            selected = isLiked,
            enabled = enabled,
            count = likes,
            icon = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            description = stringResource(if (isLiked) R.string.review_remove_like else R.string.review_like),
            tag = "btnLike",
            onClick = { onReactionClick(false) }
        )
        ReactionButton(
            selected = isDisliked,
            enabled = enabled,
            count = dislikes,
            icon = if (isDisliked) Icons.Filled.ThumbDown else Icons.Outlined.ThumbDown,
            description = stringResource(if (isDisliked) R.string.review_remove_dislike else R.string.review_dislike),
            tag = "btnDislike",
            onClick = { onReactionClick(true) }
        )
    }
}

@Composable
private fun ReactionButton(
    selected: Boolean,
    enabled: Boolean,
    count: Int,
    icon: ImageVector,
    description: String,
    tag: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconToggleButton(
            checked = selected,
            enabled = enabled,
            onCheckedChange = { onClick() },
            modifier = Modifier.testTag(tag)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = if (selected) Color(0xFFFF2E93) else Color.Gray
            )
        }
        Text(text = "$count", color = Color.White, fontSize = 12.sp)
    }
}
