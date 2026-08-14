package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun Descripcion(
    descripcion: String,
    modifier: Modifier = Modifier
){
    Text(
        text = descripcion,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.Voxel_secundario),
        modifier = modifier
    )

}