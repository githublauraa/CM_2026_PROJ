package com.example.voxel_review

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.voxel_review.navigation.AppNavigation
import com.example.voxel_review.navigation.AppScreen
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar

/**
 * Componente principal de la aplicación.
 * Configura la navegación y controla la visualización de la barra
 * de navegación inferior según la pantalla actual.
 *
 * @param modifier Modificador aplicado al contenido principal de la aplicación.
 */
@Composable
fun VoxelReviewApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    // Determina en qué pantallas debe mostrarse la barra de navegación inferior.
    val showBar = currentRoute != AppScreen.Start.route &&
            currentRoute != AppScreen.Register.route &&
            currentRoute != AppScreen.Notifications.route &&
            currentRoute?.startsWith(AppScreen.FullReviews.route) != true &&
            currentRoute ?.startsWith(AppScreen.WriteReview.route) != true &&
            currentRoute != AppScreen.Configuration.route &&
            currentRoute ?.startsWith(AppScreen.GameDetail.route) != true

    Scaffold(
        bottomBar = {
            if (showBar) {
                DiscoverBottomNavigationBar(
                    navController = navController
                )
            }
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = modifier.padding(paddingValues)
        )
    }
}