package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.voxel_review.R
import  com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    modifier: Modifier = Modifier
) {
    val backgroundColor = colorResource(R.color.Voxel_negro)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = backgroundColor,

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