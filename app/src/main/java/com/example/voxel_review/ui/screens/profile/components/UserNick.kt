package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.voxel_review.R

@Composable
fun UserNick(
    nick: String,
    modifier: Modifier = Modifier
){
    Text(
        text = nick,
        fontSize = 22.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp)

    )
}
