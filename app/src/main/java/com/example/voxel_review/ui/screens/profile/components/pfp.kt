package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun Pfp(
    image: Int,
    modifier: Modifier = Modifier
){
    Image(
	    painter = painterResource(image),
	    contentDescription = "Profile Picture",
        modifier = Modifier.width(130.dp).height(110.dp)
    )
}
