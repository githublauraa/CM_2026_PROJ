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
import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.Discover.DiscoverScreen
import com.example.voxel_review.ui.screens.writeReview.WriteReviewScreen
import com.example.voxel_review.ui.screens.rankings.RankingsScreen
import com.example.voxel_review.data.infoRanking.ListaRanking
import com.example.voxel_review.ui.screens.notifications.NotificationScreen

//sealed class
sealed class AppScreen(val route: String){
    object Start : AppScreen("start")
    object Register : AppScreen("register")
    object Reviews : AppScreen("reviews")
    object PerfilUser: AppScreen("perfilUser")
    object FullReviews : AppScreen("fullReviews")
    object Discover : AppScreen("discover")
    object WriteReview : AppScreen("writeReview")
    object RankingsUser : AppScreen("rankingsUser")
    object Configuration : AppScreen("configuration")
    object Notifications : AppScreen("notifications")
}


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Start.route,
        modifier = modifier
    ){
        composable(route = AppScreen.Start.route){
            StartScreen(
                logginButtonPressed = {
                    navController.navigate(AppScreen.Reviews.route){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                },
                createAccountButtonPressed = {
                    navController.navigate(AppScreen.Register.route)
                }
            )
        }

        composable(route = AppScreen.Register.route){
            CreateAccountScreen(
                unirseButtonPressed = {
                    navController.navigate(AppScreen.Reviews.route){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }
            )

        }

        composable(route = AppScreen.Reviews.route){
            NovedadScreen(
                listaJuegos = LocalJuegosProvider.juegos
            )
        }

        composable(route = AppScreen.PerfilUser.route){
            ProfileScreen()
        }

        composable(route = AppScreen.PerfilUser.route){
            ProfileScreen()
        }

        composable(route = AppScreen.Discover.route){
            DiscoverScreen()
        }

        composable(route = AppScreen.WriteReview.route){
            WriteReviewScreen()
        }

        composable(route = AppScreen.RankingsUser.route){
            RankingsScreen(
                rankingUsuarios = ListaRanking.rankingUsuarios
            )
        }
        //falta reseña completa, configuracion
        composable(route = AppScreen.Notifications.route){
            NotificationScreen()
        }



    }

}
