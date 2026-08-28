package com.example.voxel_review.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voxel_review.ui.screens.home.StartScreen
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountScreen
import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.data.LocalJuegosProvider


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "start",
        modifier = modifier
    ){
        composable(route = "start"){
            StartScreen(
                logginButtonPressed = {
                    navController.navigate("reviews"){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                },
                createAccountButtonPressed = {
                    navController.navigate("register")
                }
            )
        }

        composable(route = "register"){
            CreateAccountScreen()
        }

        composable(route = "reviews"){
            NovedadScreen(
                listaJuegos = LocalJuegosProvider.juegos
            )
        }

    }

}
