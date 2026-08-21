package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.screens.review.components.writeReview.BotonPublicarReview
import com.example.voxel_review.ui.screens.review.components.writeReview.GameCalification
import com.example.voxel_review.ui.screens.review.components.writeReview.GameInfo
import com.example.voxel_review.ui.screens.review.components.writeReview.ReviewText
import com.example.voxel_review.ui.screens.review.components.writeReview.TopBar
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.DiscoverTab
import com.example.voxel_review.ui.utils.FondoPantalla
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            ReviewDetailTopBar()
            ReviewDetailContent()
        }
        ReviewBottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun ReviewDetailScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize(),
        )
        ReviewDetailScreenContent(
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun WriteReviewScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp), 
            modifier = Modifier.align(Alignment.TopCenter)
            .fillMaxWidth(0.9f)
        ) {
            TopBar()
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
        ReviewBottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun WriteReviewScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize(),
        )
        WriteReviewScreenContent(
            modifier = Modifier.fillMaxSize(),
        )
    }
} 

@Composable
@Preview
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen()
}

@Composable
@Preview
fun WriteReviewScreenPreview() {
    WriteReviewScreen()
}