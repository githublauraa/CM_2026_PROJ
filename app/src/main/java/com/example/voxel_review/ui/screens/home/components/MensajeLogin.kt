package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R


@Composable
fun MensajeLogin(
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(R.string.login_registro),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color =  Color.White,
        modifier = modifier
    )
}