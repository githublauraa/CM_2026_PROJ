package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun UserNick(
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(R.string.nick),
        fontSize = 25.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        modifier = modifier

    )
}
