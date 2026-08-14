package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun Subtitulo(
    modifire: Modifier = Modifier
){
    Text(
        text = stringResource(R.string.explora_rese_a_crea),
        color = colorResource(R.color.Voxel_secundario),
        modifier = Modifier.padding(8.dp)

    )
}

