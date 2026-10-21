package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
@Composable
fun LikeCard(
    isLiked: Boolean,
    likes: Int,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onLikeClick,
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite
                else Icons.Outlined.FavoriteBorder,
                contentDescription = "Dar like",
                tint = if (isLiked) Color(0xFFFF2E93)
                else Color.Gray
            )
        }

        Text(
            text = "$likes",
            color = Color.White,
            fontSize = 12.sp
        )

    }
}


@Preview
@Composable
fun LikeCardPreview(){
    LikeCard(
        likes = 1,
        isLiked = true,
        onLikeClick = {}
    )
}