package com.example.voxel_review

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.voxel_review.navigation.AppNavigation
import com.example.voxel_review.navigation.AppScreen
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.google.firebase.messaging.FirebaseMessaging

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

    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Token", task.result)
            } else {
                Log.d("Token", "Error getting token", task.exception)
            }
        }
    }

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


    //prueba para express con retrofit
//    LaunchedEffect(Unit) {
//        navController.navigate("${AppScreen.PerfilUser.route}?profileId=2")
//    }
}
