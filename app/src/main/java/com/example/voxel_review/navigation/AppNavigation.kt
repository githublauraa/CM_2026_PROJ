package com.example.voxel_review.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.voxel_review.data.InfoDiscover.LocalTrendingSearchProvider
import com.example.voxel_review.ui.screens.start.StartScreen
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountScreen
import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.data.LocalJuegosProvider
import com.example.voxel_review.ui.screens.Discover.DiscoverScreen
import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.writeReview.WriteReviewRoute
import com.example.voxel_review.ui.screens.writeReview.WriteReviewViewModel
import com.example.voxel_review.ui.screens.rankings.RankingsScreen
import com.example.voxel_review.ui.screens.Discover.DiscoverViewModel
import com.example.voxel_review.ui.screens.GameDetail.GameDetailContent
import com.example.voxel_review.ui.screens.GameDetail.GameDetailViewModel
import com.example.voxel_review.ui.screens.Splash.SplashScreen
import com.example.voxel_review.ui.screens.notifications.NotificationsViewModel
import com.example.voxel_review.ui.screens.review.ReviewDetailScreen
import com.example.voxel_review.ui.screens.settings.SettingsRoute
import com.example.voxel_review.ui.screens.profile.ProfileViewModel
import com.example.voxel_review.ui.screens.start.StartViewModel
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountViewModel
import com.example.voxel_review.ui.screens.notifications.NotificationContent
import com.example.voxel_review.ui.screens.notifications.components.NotificationsContent
import com.example.voxel_review.ui.screens.novedades.NovedadesViewModel
import com.example.voxel_review.ui.screens.rankings.RankingsViewModel
import com.example.voxel_review.ui.screens.review.ReviewViewModel
import com.example.voxel_review.ui.screens.settings.SettingsViewModel
import com.example.voxel_review.ui.screens.Splash.SplashViewModel
/**
 * Define las rutas disponibles dentro de la navegación de la aplicación.
 *
 * @param route Identificador utilizado por Navigation Compose para cada pantalla.
 */
sealed class AppScreen(val route: String) {
    object Splash : AppScreen("splash")
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
        startDestination = AppScreen.Splash.route,
        modifier = modifier
    ) {

        composable(route = AppScreen.Splash.route) {
            SplashScreen(
                splashViewModel = hiltViewModel(),
                onNavigateHome = {
                    navController.navigate(AppScreen.RankingsUser.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateStart = {
                    navController.navigate(AppScreen.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppScreen.Start.route) {
            val startViewModel: StartViewModel = hiltViewModel()

            StartScreen(
                startViewModel = startViewModel,
                logginButtonPressed = {
                    navController.navigate(AppScreen.RankingsUser.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                createAccountButtonPressed = {
                    navController.navigate(AppScreen.Register.route)
                }
            )
        }

        composable(route = AppScreen.Register.route) {
            val createAccountViewModel: CreateAccountViewModel = hiltViewModel()

            CreateAccountScreen(
                createAccountViewModel = createAccountViewModel,
                unirseButtonPressed = {
                    navController.navigate(AppScreen.RankingsUser.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppScreen.Reviews.route) {
            val novedadesViewModel: NovedadesViewModel = hiltViewModel()

            NovedadScreen(
                novedadesViewModel,
                onClick = { juego ->
                    // CORREGIDO: Se envía directamente el id de tipo String en lugar de usar indexOf
                    navController.navigate(
                        "${AppScreen.FullReviews.route}?juegoId=${juego.id}"
                    )
                },
                onNotificationClick = {
                    navController.navigate(AppScreen.Notifications.route)
                }
            )
        }

        // CORREGIDO: Se incluye ?profileId={profileId} en la ruta y defaultValue en String "1"
        composable(
            route = "${AppScreen.PerfilUser.route}?profileId={profileId}",
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.StringType
                    defaultValue = "2"
                }
            )
        ) { backStackEntry ->

            val profileId = backStackEntry.arguments?.getString("profileId") ?: "1"

            ProfileScreen(
                profileViewModel = hiltViewModel(),
                profileId = profileId,
                onBackClick = {
                    navController.popBackStack()
                },
                onClickImage = {},
                onReviewClick = { review ->
                    navController.navigate(
                        "${AppScreen.FullReviews.route}?reviewId=${review.idResenia}&userId=$profileId"
                    )
                },
                buttonLogOutPressed = {
                    navController.navigate(AppScreen.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppScreen.Configuration.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            SettingsRoute(
                settingsViewModel = settingsViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppScreen.Discover.route) {
            val discoverViewModel: DiscoverViewModel = hiltViewModel()

            DiscoverScreen(
                discoverViewModel = discoverViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(AppScreen.Notifications.route)
                },
                onItemClick = { item ->
                    // CORREGIDO: Se envía el id de tipo String en lugar de indexOf
                    navController.navigate(
                        "${AppScreen.GameDetail.route}?gameId=${item.id}"
                    )
                }
            )
        }

        composable(
            route = "${AppScreen.GameDetail.route}?gameId={gameId}",
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) { backStackEntry ->

            val gameDetailViewModel: GameDetailViewModel = hiltViewModel()
            val gameId = backStackEntry.arguments?.getString("gameId") ?: "1"

            GameDetailContent(
                gameDetailViewModel = gameDetailViewModel,
                gameId = gameId,
                onBackPressed = {
                    navController.popBackStack()
                },
                onSearchPressed = {
                    navController.navigate(AppScreen.Discover.route)
                },
                onWriteReviewPressed = {
                    navController.navigate("${AppScreen.WriteReview.route}?gameId=$gameId")
                }
            )
        }

        composable(route = AppScreen.RankingsUser.route) {
            val rankingsViewModel: RankingsViewModel = hiltViewModel()

            RankingsScreen(
                rankingsViewModel = rankingsViewModel
            )
        }

        composable(route = AppScreen.Notifications.route) {
            val notificationsViewModel: NotificationsViewModel = hiltViewModel()

            NotificationContent(
                notificationsViewModel = notificationsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(AppScreen.Discover.route)
                }
            )
        }

        // CORREGIDO: Renombrado juegoIndex a juegoId y agregado defaultValue "1"
        // reviewId/userId permiten abrir una reseña concreta desde el perfil del usuario.
        composable(
            route = "${AppScreen.FullReviews.route}?juegoId={juegoId}&reviewId={reviewId}&userId={userId}",
            arguments = listOf(
                navArgument("juegoId") {
                    type = NavType.StringType
                    defaultValue = "1"
                },
                navArgument("reviewId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val reviewViewModel: ReviewViewModel = hiltViewModel()
            val juegoId = backStackEntry.arguments?.getString("juegoId") ?: "1"
            val reviewId = backStackEntry.arguments?.getString("reviewId") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            ReviewDetailScreen(
                reviewViewModel = reviewViewModel,
                profileViewModel = hiltViewModel(),
                juegoId = juegoId,
                reviewId = reviewId,
                userId = userId,
                onCommentClick = { juegoId ->
                navController.navigate("${AppScreen.WriteReview.route}?gameId=$juegoId")
                },
                onBackClick = {
                    navController.popBackStack()
                },

                onClickReview = {
                    navController.navigate("${AppScreen.WriteReview.route}?gameId=$juegoId")
                },
                onEditReview = { review ->
                    navController.navigate(
                        "${AppScreen.WriteReview.route}?gameId=${review.videoGameId}&reviewId=${review.idResenia}&userId=${review.userId}"
                    )
                }
            )
        }

        // CORREGIDO: defaultValue = "1" en lugar de defaultValue = 1
        // reviewId/userId habilitan el modo edición (actualizar reseña existente)
        composable(
            route = "${AppScreen.WriteReview.route}?gameId={gameId}&reviewId={reviewId}&userId={userId}",
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.StringType
                    defaultValue = "1"
                },
                navArgument("reviewId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val gameId = backStackEntry.arguments?.getString("gameId") ?: "1"
            val reviewId = backStackEntry.arguments?.getString("reviewId") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            WriteReviewRoute(
                writeReviewViewModel = writeReviewViewModel,
                profileViewModel = profileViewModel,
                gameId = gameId,
                reviewId = reviewId,
                userId = userId,
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
