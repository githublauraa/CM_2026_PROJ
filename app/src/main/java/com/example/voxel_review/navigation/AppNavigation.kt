package com.example.voxel_review.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.voxel_review.data.InfoDiscover.LocalTrendingSearchProvider
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.ui.screens.start.StartScreen
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountScreen
import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.data.LocalJuegosProvider
import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.writeReview.WriteReviewScreen
import com.example.voxel_review.ui.screens.rankings.RankingsScreen
import com.example.voxel_review.ui.screens.Discover.DiscoverRoute
import com.example.voxel_review.ui.screens.Discover.DiscoverViewModel
import com.example.voxel_review.ui.screens.GameDetail.GameDetailRoute
import com.example.voxel_review.ui.screens.GameDetail.GameDetailViewModel
import com.example.voxel_review.ui.screens.notifications.NotificationRoute
import com.example.voxel_review.ui.screens.notifications.NotificationsViewModel
import com.example.voxel_review.ui.screens.review.ReviewDetailScreen
import com.example.voxel_review.ui.screens.settings.SettingsRoute
import com.example.voxel_review.ui.screens.profile.ProfileViewModel
import com.example.voxel_review.ui.screens.start.StartViewModel
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountViewModel
import com.example.voxel_review.ui.screens.novedades.NovedadesViewModel
import com.example.voxel_review.ui.screens.rankings.RankingsViewModel
import com.example.voxel_review.ui.screens.settings.SettingsViewModel

/**
 * Define las rutas disponibles dentro de la navegación de la aplicación.
 *
 * @param route Identificador utilizado por Navigation Compose para cada pantalla.
 */
sealed class AppScreen(val route: String) {
    object Start : AppScreen("start")
    object Register : AppScreen("register")
    object Reviews : AppScreen("reviews")
    object PerfilUser : AppScreen("perfilUser")
    object FullReviews : AppScreen("fullReviews")
    object Discover : AppScreen("discover")
    object GameDetail : AppScreen("gameDetail")
    object WriteReview : AppScreen("writeReview")
    object RankingsUser : AppScreen("rankingsUser")
    object Configuration : AppScreen("configuration")
    object Notifications : AppScreen("notifications")
}

/**
 * Configura las rutas y la navegación principal de la aplicación.
 * Cada destino crea la pantalla correspondiente y define las acciones
 * necesarias para navegar entre las diferentes secciones.
 *
 * @param navController Controlador encargado de administrar la navegación.
 * @param modifier Modificador aplicado al contenedor de navegación.
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Start.route,
        modifier = modifier
    ) {

        composable(route = AppScreen.Start.route) {
            val startViewModel: StartViewModel = viewModel()

            StartScreen(
                startViewModel = startViewModel,
                logginButtonPressed = {
                    navController.navigate(AppScreen.Reviews.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                createAccountButtonPressed = {
                    navController.navigate(AppScreen.Register.route)
                }
            )
        }

        composable(route = AppScreen.Register.route) {
            val createAccountViewModel: CreateAccountViewModel = viewModel()

            CreateAccountScreen(
                createAccountViewModel = createAccountViewModel,
                unirseButtonPressed = {
                    navController.navigate(AppScreen.Reviews.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = AppScreen.Reviews.route) {
            val novedadesViewModel: NovedadesViewModel = viewModel()

            NovedadScreen(
                novedadesViewModel,
                onClick = { juego ->

                    // Obtiene el índice del juego para enviarlo como argumento a la siguiente pantalla.
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
                }
            )
        }

        composable(route = AppScreen.PerfilUser.route) {
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
            val settingsViewModel: SettingsViewModel = viewModel()
            SettingsRoute(
                settingsViewModel = settingsViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppScreen.Discover.route) {
            val discoverViewModel: DiscoverViewModel = viewModel()

            DiscoverRoute(
                discoverViewModel = discoverViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(AppScreen.Notifications.route)
                },
                onItemClick = { item ->

                    // Envía la posición del juego seleccionado a la pantalla de detalle.
                    val gameIndex =
                        LocalTrendingSearchProvider.tendencias.indexOf(item)

                    navController.navigate(
                        "${AppScreen.GameDetail.route}?gameIndex=$gameIndex"
                    )
                }
            )
        }

        // Ruta que recibe el índice del juego seleccionado como argumento.
        composable( route = "${AppScreen.GameDetail.route}?gameIndex={gameIndex}",
            arguments = listOf(
                navArgument("gameIndex") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->

            val gameDetailViewModel: GameDetailViewModel = viewModel()
            val gameIndex =
                backStackEntry.arguments?.getInt("gameIndex") ?: 0

            GameDetailRoute(
                gameDetailViewModel = gameDetailViewModel,
                gameIndex = gameIndex,
                onBackPressed = {
                    navController.popBackStack()
                },
                onSearchPressed = {
                    navController.navigate(AppScreen.Discover.route)
                },
                onWriteReviewPressed = {
                    navController.navigate(AppScreen.WriteReview.route)
                }
            )
        }

        composable(route = AppScreen.RankingsUser.route) {
            val rankingsViewModel: RankingsViewModel = viewModel()

            RankingsScreen(
                rankingsViewModel = rankingsViewModel
            )
        }

        composable(route = AppScreen.Notifications.route) {
            val notificationsViewModel: NotificationsViewModel = viewModel()

            NotificationRoute(
                notificationsViewModel = notificationsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
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

        composable(route = AppScreen.WriteReview.route) {
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