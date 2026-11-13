package com.example.voxel_review.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.voxel_review.R

@Composable
fun GameImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    // Foto de Benedict Calano (Unsplash): https://unsplash.com/photos/WBfhrZA2TAE
    val fallback = painterResource(R.drawable.game_banner_fallback)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl.takeIf { it.isNotBlank() })
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.loading_img),
        fallback = fallback,
        error = fallback,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
