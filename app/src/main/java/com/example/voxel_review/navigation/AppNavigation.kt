package com.example.voxel_review.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
import com.example.voxel_review.ui.screens.review.ReviewDetailScreen

//sealed class
sealed class AppScreen(val route: String){
    object Start : AppScreen("start")
    object Register : AppScreen("register")
    object Reviews : AppScreen("reviews")
    object PerfilUser: AppScreen("perfilUser")
    object FullReviews : AppScreen("fullReviews/{juegoIndex}")
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

        composable(route = AppScreen.Reviews.route) {

            NovedadScreen(
                onClick = { juego ->

                    val juegoIndex =
                        LocalJuegosProvider.juegos.indexOf(juego)

                    navController.navigate(
                        "fullReviews/$juegoIndex"
                    )
                },
                onNotificationClick = {
                    navController.navigate(
                        AppScreen.Notifications.route
                    )
                },
                listaJuegos = LocalJuegosProvider.juegos
            )
        }

        composable(route = AppScreen.PerfilUser.route){
            ProfileScreen()
        }

        composable(route = AppScreen.Discover.route){
            DiscoverScreen(
                onNotificationClick = {
                    navController.navigate(AppScreen.Notifications.route)
                }
            )
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
        composable( route = AppScreen.FullReviews.route,
            arguments = listOf(
                navArgument("juegoIndex") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val juegoIndex =
                backStackEntry.arguments?.getInt("juegoIndex") ?: 0

            val juego =
                LocalJuegosProvider.juegos[juegoIndex]

            ReviewDetailScreen(
                juego = juego,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    }
}
