package com.example.voxel_review

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.voxel_review.navigation.AppNavigation
import com.example.voxel_review.navigation.AppScreen
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.LocalBottomBarPadding
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow

data class ChatNotificationTarget(val recipientId: String, val senderId: String)

/**
 * Componente principal de la aplicación.
 * Configura la navegación y controla la visualización de la barra
 * de navegación inferior según la pantalla actual.
 *
 * @param modifier Modificador aplicado al contenido principal de la aplicación.
 */
@Composable
fun VoxelReviewApp(
    modifier: Modifier = Modifier,
    chatNotification: ChatNotificationTarget? = null,
    onChatNotificationHandled: () -> Unit = {},
    foregroundMessages: Flow<ChatNotificationTarget> = emptyFlow(),
    onForegroundChatOpened: (ChatNotificationTarget) -> Unit = {}
) {
    val navController = rememberNavController()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    LaunchedEffect(chatNotification, currentRoute) {
        val target = chatNotification ?: return@LaunchedEffect
        if (currentRoute == null || currentRoute == AppScreen.Splash.route) return@LaunchedEffect

        if (FirebaseAuth.getInstance().currentUser?.uid == target.recipientId) {
            val openContact = currentBackStackEntry.value?.arguments?.getString("contactId")
            if (openContact != target.senderId) {
                navController.navigate(AppScreen.Chat.createRoute(target.senderId))
            }
        }
        onChatNotificationHandled()
    }

    val snackbar = remember { SnackbarHostState() }
    val notificationText = stringResource(R.string.chat_notification_title)
    val openChatLabel = stringResource(R.string.chat_notification_open)
    LaunchedEffect(foregroundMessages, notificationText, openChatLabel) {
        foregroundMessages.collectLatest { target ->
            if (FirebaseAuth.getInstance().currentUser?.uid != target.recipientId) return@collectLatest

            val openContact = navController.currentBackStackEntry?.arguments?.getString("contactId")
            if (openContact == target.senderId) return@collectLatest

            val result = snackbar.showSnackbar(
                message = notificationText,
                actionLabel = openChatLabel,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) onForegroundChatOpened(target)
        }
    }

    // Determina en qué pantallas debe mostrarse la barra de navegación inferior.
    val showBar = currentRoute != null &&
            currentRoute != AppScreen.Splash.route &&
            currentRoute != AppScreen.Start.route &&
            currentRoute != "${AppScreen.Chat.route}/{contactId}" &&
            currentRoute != AppScreen.Register.route &&
            currentRoute != AppScreen.ForgotPassword.route &&
            currentRoute != AppScreen.Notifications.route &&
            currentRoute?.startsWith(AppScreen.FullReviews.route) != true &&
            currentRoute ?.startsWith(AppScreen.WriteReview.route) != true &&
            currentRoute != AppScreen.Configuration.route &&
            currentRoute ?.startsWith(AppScreen.GameDetail.route) != true

    val barOverlaysContent = showBar && currentRoute?.substringBefore("?") in setOf(
        AppScreen.RankingsUser.route,
        AppScreen.Discover.route,
        AppScreen.Reviews.route,
        AppScreen.Chats.route,
        AppScreen.PerfilUser.route
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbar) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = VoxelSurfaceVariant,
                    contentColor = VoxelTextPrimary,
                    actionColor = VoxelPrimary
                )
            }
        },
        bottomBar = {
            if (showBar) {
                DiscoverBottomNavigationBar(
                    navController = navController
                )
            }
        }
    ) { paddingValues ->
        val layoutDirection = LocalLayoutDirection.current
        val bottomPadding = paddingValues.calculateBottomPadding()
        val screenPadding = PaddingValues(
            start = paddingValues.calculateStartPadding(layoutDirection),
            top = paddingValues.calculateTopPadding(),
            end = paddingValues.calculateEndPadding(layoutDirection),
            bottom = if (barOverlaysContent) 0.dp else bottomPadding
        )

        CompositionLocalProvider(
            LocalBottomBarPadding provides if (barOverlaysContent) bottomPadding else 0.dp
        ) {
            AppNavigation(
                navController = navController,
                modifier = modifier.padding(screenPadding)
            )
        }
    }


    //prueba para express con retrofit
//    LaunchedEffect(Unit) {
//        navController.navigate("${AppScreen.PerfilUser.route}?profileId=2")
//    }
}
