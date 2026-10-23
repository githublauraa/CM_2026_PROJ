package com.example.voxel_review.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.google.firebase.auth.FirebaseAuth

import com.example.voxel_review.ui.screens.start.StartScreen
import com.example.voxel_review.ui.screens.start.StartViewModel

import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountScreen
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountViewModel

import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.ui.screens.novedades.NovedadesViewModel

import com.example.voxel_review.ui.screens.Discover.DiscoverScreen
import com.example.voxel_review.ui.screens.Discover.DiscoverViewModel

import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.profile.ProfileViewModel

import com.example.voxel_review.ui.screens.writeReview.WriteReviewRoute
import com.example.voxel_review.ui.screens.writeReview.WriteReviewViewModel

import com.example.voxel_review.ui.screens.rankings.RankingsScreen
import com.example.voxel_review.ui.screens.rankings.RankingsViewModel

import com.example.voxel_review.ui.screens.GameDetail.GameDetailContent
import com.example.voxel_review.ui.screens.GameDetail.GameDetailViewModel

import com.example.voxel_review.ui.screens.Splash.SplashScreen

import com.example.voxel_review.ui.screens.notifications.NotificationContent
import com.example.voxel_review.ui.screens.notifications.NotificationsViewModel

import com.example.voxel_review.ui.screens.review.ReviewDetailScreen
import com.example.voxel_review.ui.screens.review.ReviewViewModel

import com.example.voxel_review.ui.screens.settings.SettingsRoute
import com.example.voxel_review.ui.screens.settings.SettingsViewModel

import com.example.voxel_review.ui.screens.editProfile.EditProfileScreen


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

    object EditProfile : AppScreen("editProfile")

    object UserProfile: AppScreen("userProfile"){
        fun createRoute(userdId: String,showEdit: Boolean = false) = "userProfile/$userdId/$showEdit"
    }
}


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

        // SPLASH
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


        // LOGIN
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

        // EDITAR PERFIL
        composable(route = AppScreen.EditProfile.route) {
            EditProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        // REGISTRO
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


        // NOVEDADES
        composable(route = AppScreen.Reviews.route) {

            val novedadesViewModel: NovedadesViewModel = hiltViewModel()

            NovedadScreen(
                novedadesViewModel,
                onClick = { juego ->
                    navController.navigate(
                        "${AppScreen.FullReviews.route}?juegoId=${juego.id}"
                    )
                },
                onNotificationClick = {
                    navController.navigate(AppScreen.Notifications.route)
                },
                onReviewClick = { review ->
                    navController.navigate(
                        "${AppScreen.FullReviews.route}?reviewId=${review.idResenia}&userId=${review.userId}"
                    )
                },
                onUserProfileClick = { userId ->
                    if (userId.isNotBlank()) {
                        navController.navigate(
                            "${AppScreen.PerfilUser.route}?profileId=$userId"
                        )
                    }
                }
            )
        }


        // PERFIL
        composable(
            route = "${AppScreen.PerfilUser.route}?profileId={profileId}",
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            val currentUser = FirebaseAuth.getInstance().currentUser

            val profileId = backStackEntry.arguments
                ?.getString("profileId")
                ?.takeIf { it.isNotBlank() }
                ?: currentUser?.uid.orEmpty()

            if (profileId.isNotBlank()) {

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
                    },
                    onEditProfileClick = {
                        navController.navigate(AppScreen.EditProfile.route){
                            navController.popBackStack()
                        }
                    },
                )
            }
        }


        // CONFIGURACIÓN
        composable(route = AppScreen.Configuration.route) {

            val settingsViewModel: SettingsViewModel = hiltViewModel()

            SettingsRoute(
                settingsViewModel = settingsViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        // DISCOVER
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
                    navController.navigate(
                        "${AppScreen.GameDetail.route}?gameId=${item.id}"
                    )
                }
            )
        }


        // DETALLE DEL JUEGO
        composable(
            route = "${AppScreen.GameDetail.route}?gameId={gameId}",
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            val gameDetailViewModel: GameDetailViewModel = hiltViewModel()

            val gameId = backStackEntry.arguments
                ?.getString("gameId")
                .orEmpty()

            GameDetailContent(
                onReviewProfileImageCliked = { userId ->
                    if (userId.isNotBlank()) {
                        navController.navigate(
                            "${AppScreen.PerfilUser.route}?profileId=$userId"
                        )
                    }
                },
                gameDetailViewModel = gameDetailViewModel,
                gameId = gameId,
                onBackPressed = {
                    navController.popBackStack()
                },
                onSearchPressed = {
                    navController.navigate(AppScreen.Discover.route)
                },
                onWriteReviewPressed = {
                    navController.navigate(
                        "${AppScreen.WriteReview.route}?gameId=$gameId"
                    )
                },
                onReviewClick = { review ->
                    navController.navigate(
                        "${AppScreen.FullReviews.route}?juegoId=${review.videoGameId}&reviewId=${review.idResenia}&userId=${review.userId}"
                    )
                }
            )
        }


        // RANKINGS
        composable(route = AppScreen.RankingsUser.route) {

            val rankingsViewModel: RankingsViewModel = hiltViewModel()

            RankingsScreen(
                rankingsViewModel = rankingsViewModel,
                onUserClick = { usuario ->
                    navController.navigate(
                        "${AppScreen.PerfilUser.route}?profileId=${usuario.id}"
                    )
                }
            )
        }


        // NOTIFICACIONES
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


        // DETALLE DE RESEÑA
        composable(
            route = "${AppScreen.FullReviews.route}?juegoId={juegoId}&reviewId={reviewId}&userId={userId}",
            arguments = listOf(
                navArgument("juegoId") {
                    type = NavType.StringType
                    defaultValue = ""
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

            val juegoId = backStackEntry.arguments
                ?.getString("juegoId")
                .orEmpty()

            val reviewId = backStackEntry.arguments
                ?.getString("reviewId")
                .orEmpty()

            val userId = backStackEntry.arguments
                ?.getString("userId")
                .orEmpty()

            ReviewDetailScreen(
                reviewViewModel = reviewViewModel,
                profileViewModel = hiltViewModel(),
                juegoId = juegoId,
                reviewId = reviewId,
                userId = userId,
                onCommentClick = { selectedGameId ->
                    navController.navigate(
                        "${AppScreen.WriteReview.route}?gameId=$selectedGameId"
                    )
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onClickReview = {
                    navController.navigate(
                        "${AppScreen.WriteReview.route}?gameId=$juegoId"
                    )
                },
                onEditReview = { review ->
                    navController.navigate(
                        "${AppScreen.WriteReview.route}?gameId=${review.videoGameId}&reviewId=${review.idResenia}&userId=${review.userId}"
                    )
                },
                onReviewProfileImageClicked = { selectedUserId ->
                    if (selectedUserId.isNotBlank()) {
                        navController.navigate(
                            "${AppScreen.PerfilUser.route}?profileId=$selectedUserId"
                        )
                    }
                }
            )
        }


        // ESCRIBIR RESEÑA
        composable(
            route = "${AppScreen.WriteReview.route}?gameId={gameId}&reviewId={reviewId}&userId={userId}",
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.StringType
                    defaultValue = ""
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

            val gameId = backStackEntry.arguments
                ?.getString("gameId")
                .orEmpty()

            val reviewId = backStackEntry.arguments
                ?.getString("reviewId")
                .orEmpty()

            val userId = backStackEntry.arguments
                ?.getString("userId")
                .orEmpty()

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