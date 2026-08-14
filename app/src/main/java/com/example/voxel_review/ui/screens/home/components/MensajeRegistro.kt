package com.example.voxel_review.ui.screens.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun MensajeUsuario(
    modifier: Modifier = Modifier,
    tipo: String
){
    Text(
        text = tipo,
        color = colorResource(R.color.Voxel_secundario),
        fontSize = 20.sp,
        modifier = modifier
    )
}