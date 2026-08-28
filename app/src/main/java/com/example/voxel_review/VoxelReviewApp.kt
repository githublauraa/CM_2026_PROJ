package com.example.voxel_review

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.voxel_review.navigation.AppNavigation
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.voxel_review.navigation.AppScreen


@Composable
fun VoxelReviewApp(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val showBar = currentRoute != AppScreen.Start.route && currentRoute != AppScreen.Register.route

    Scaffold(

        bottomBar = {
            if (showBar){
                DiscoverBottomNavigationBar(
                    navController = navController
                )
            }
        }
    ) {
        AppNavigation(
            navController = navController,
            modifier = modifier.padding(it)
        )
    }
}