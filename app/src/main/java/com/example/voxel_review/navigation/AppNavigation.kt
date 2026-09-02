package com.example.voxel_review.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.voxel_review.ui.screens.start.StartScreen
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountScreen
import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.data.LocalJuegosProvider
import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.writeReview.WriteReviewScreen
import com.example.voxel_review.ui.screens.rankings.RankingsScreen
import com.example.voxel_review.data.infoRanking.ListaRanking
import com.example.voxel_review.ui.screens.Discover.DiscoverRoute
import com.example.voxel_review.ui.screens.Discover.DiscoverViewModel
import com.example.voxel_review.ui.screens.notifications.NotificationRoute
import com.example.voxel_review.ui.screens.notifications.NotificationsViewModel
import com.example.voxel_review.ui.screens.review.ReviewDetailScreen
import com.example.voxel_review.ui.screens.settings.SettingsRoute
import  com.example.voxel_review.ui.screens.profile.ProfileViewModel
import com.example.voxel_review.ui.screens.start.StartViewModel
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
            val startViewModel: StartViewModel = viewModel()
            StartScreen(
                startViewModel = startViewModel,
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
                        "${AppScreen.FullReviews.route}?juegoIndex=$juegoIndex"
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
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(
                profileViewModel = profileViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onClickImage = {
                    navController.navigate(AppScreen.Configuration.route)
                }
            )
        }

        composable(route = AppScreen.Configuration.route) {
            SettingsRoute(
                onBackClick = {
                    navController.popBackStack()
                },
                darkMode = false,
                onDarkModeChange = {}
            )
        }



        composable(route = AppScreen.Discover.route){
            val discoverViewModel: DiscoverViewModel = viewModel()
            DiscoverRoute(
                discoverViewModel = discoverViewModel,
                onBackClick =  {
                    navController.popBackStack()
            },
                onNotificationClick = {
                    navController.navigate(AppScreen.Notifications.route)
                },
            )
        }


        composable(route = AppScreen.RankingsUser.route){
            RankingsScreen(
                rankingUsuarios = ListaRanking.rankingUsuarios
            )
        }


        composable(route = AppScreen.Notifications.route){
            val notificationsViewModel: NotificationsViewModel = viewModel()
            NotificationRoute(
                notificationsViewModel = notificationsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                //especificar rutas:  destacados a reseña completa, ayer a pantalla detalle y sugerencia seguiento a perfil de otro usuario
                 onNotificationClick = {
                    navController.navigate(AppScreen.Discover.route)
                }
            )
        }

        composable( route = "${AppScreen.FullReviews.route}?juegoIndex={juegoIndex}",
            arguments = listOf(
                navArgument("juegoIndex") {
                    type = NavType.IntType
                }
            )
        ) {
            ReviewDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onClickReview = {
                    navController.navigate(AppScreen.WriteReview.route)
                }
            )
        }

        composable( route = AppScreen.WriteReview.route){
            WriteReviewScreen(
                onSettingsClick = {
                    navController.navigate(AppScreen.Configuration.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    }
}
