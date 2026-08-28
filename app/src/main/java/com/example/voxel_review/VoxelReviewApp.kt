package com.example.voxel_review

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.navigation.AppNavigation
import androidx.navigation.compose.rememberNavController
@Composable
fun VoxelReviewApp(
    modifier: Modifier = Modifier
) {
    Scaffold(
    ) {
        var navController = rememberNavController()
        AppNavigation(
            navController = navController,
            modifier = modifier.padding(it))
    }
}
