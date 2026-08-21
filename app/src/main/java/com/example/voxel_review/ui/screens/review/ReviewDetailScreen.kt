package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.theme.VoxelBackgroundDark
import androidx.compose.ui.tooling.preview.Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = VoxelBackgroundDark,

        topBar = {
            ReviewDetailTopBar()
        },

        bottomBar = {
            ReviewBottomNavigationBar()
        }

    ) { innerPadding ->

        ReviewDetailContent(
            innerPadding = innerPadding
        )
    }
}

@Preview
@Composable
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen()
}
