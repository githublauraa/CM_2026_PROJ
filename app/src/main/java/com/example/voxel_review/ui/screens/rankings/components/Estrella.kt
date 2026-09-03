package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.voxel_review.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding

@Composable
fun Estrella(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_star),
        contentDescription = "Estrella",
        modifier = modifier
            .height(70.dp)
            .padding(18.dp)
    )
}

@Preview
@Composable
fun EstrellaPreview() {
    Estrella()
}
