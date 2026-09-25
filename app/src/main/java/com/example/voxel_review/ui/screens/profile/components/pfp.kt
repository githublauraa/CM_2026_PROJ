package com.example.voxel_review.ui.screens.profile.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.profile.ProfileState
@Composable
fun Pfp(
    profileImage: String?,
    modifier: Modifier = Modifier
){
    AsyncImage(
        contentDescription = "User image",
        model = ImageRequest.Builder(LocalContext.current)
            .data(profileImage)
            .crossfade(true)
            .build(),

        fallback = painterResource(id = R.drawable.imagen_login_user),
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.imagen_login_user),

        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
}
