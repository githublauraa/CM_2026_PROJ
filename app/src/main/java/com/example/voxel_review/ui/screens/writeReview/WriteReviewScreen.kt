package com.example.voxel_review.ui.screens.writeReview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.review.ReviewBottomNavigationBar
import com.example.voxel_review.ui.screens.writeReview.components.BotonPublicarReview
import com.example.voxel_review.ui.screens.writeReview.components.GameCalification
import com.example.voxel_review.ui.screens.writeReview.components.GameInfo
import com.example.voxel_review.ui.screens.writeReview.components.ReviewText
import com.example.voxel_review.ui.screens.writeReview.components.TopBar
import com.example.voxel_review.ui.utils.FondoPantalla
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun WriteReviewScreenContent(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.TopCenter)
                .fillMaxWidth(0.9f)
        ) {
            TopBar(
                onBackClick = onBackClick,
                onSettingsClick = onSettingsClick
            )
            GameInfo(
                imagen = R.drawable.tlouii,
                nombre = "The Last of Us Part II",
                desarrollador = "Naughty Dog",
                anio = "2020",
            )
            GameCalification()
            ReviewText()
            BotonPublicarReview()
        }
    }
}

@Composable
fun WriteReviewScreen(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize(),
        )
        WriteReviewScreenContent(
            onBackClick = onBackClick,
            onSettingsClick = onSettingsClick,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
@Preview
fun WriteReviewScreenPreview() {
    WriteReviewScreen(
        onBackClick = {},
        onSettingsClick = {}
    )
}
