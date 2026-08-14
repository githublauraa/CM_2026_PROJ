package com.example.voxel_review.ui.screens.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun MensajeTerminos(
    modifier: Modifier = Modifier
){
    Text(
        text = "Al continuar acepta nuestros términos de servicio",
        color = colorResource(R.color.Voxel_secundario),
        fontSize = 15.sp
    )
}