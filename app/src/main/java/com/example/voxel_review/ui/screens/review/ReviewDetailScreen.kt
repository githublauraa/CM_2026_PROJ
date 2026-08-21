package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.screens.review.components.writeReview.TopBar
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.DiscoverTab
import com.example.voxel_review.ui.utils.FondoPantalla

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreenContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            ReviewDetailTopBar()
            ReviewDetailContent()
        }

        DiscoverBottomNavigationBar(
            selectedTab = DiscoverTab.FAVORITES,
            onTabSelected = {},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ReviewDetailScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        ReviewDetailScreenContent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun WriteReviewScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            TopBar()
        }
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