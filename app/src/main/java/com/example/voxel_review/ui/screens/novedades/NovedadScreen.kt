package com.example.voxel_review.ui.screens.novedades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.novedades.components.NovedadesContent
import com.example.voxel_review.ui.utils.BarraInferior
import com.example.voxel_review.ui.utils.FondoPantalla


@Composable
fun NovedadBody(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NovedadesContent()
    }
}


@Composable
fun NovedadScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        FondoPantalla(modifier = Modifier.fillMaxSize())
        NovedadBody()

        BarraInferior(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
fun NovedadScreenPreview() {
    NovedadScreen()
}