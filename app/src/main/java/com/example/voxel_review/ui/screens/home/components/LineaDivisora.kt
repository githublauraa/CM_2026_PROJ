package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R


@Composable
fun LineaDivisora(
    modifier: Modifier = Modifier
){
    HorizontalDivider(
        modifier = Modifier.width(130.dp),
        color = colorResource(R.color.Voxel_secundario)
    )
}