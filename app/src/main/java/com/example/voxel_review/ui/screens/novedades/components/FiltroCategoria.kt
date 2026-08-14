package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun FiltroCategoria(
    texto: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = texto,
        color = colorResource(R.color.Voxel_secundario),
        fontSize = 14.sp,
        modifier = modifier
            .background(
                color = colorResource(R.color.Voxel_moradooo),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(R.color.Voxel_morado_oscuro),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = 17.dp,
                vertical = 8.dp
            )
    )
}