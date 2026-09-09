package com.example.voxel_review.ui.screens.Splash


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onNavigateHome: () -> Unit,
    onNavigateStart: () -> Unit
){
    val navigateHome by splashViewModel.navigateHome.collectAsState()

    if (navigateHome){
        onNavigateHome()
    } else {
        onNavigateStart()
    }
}