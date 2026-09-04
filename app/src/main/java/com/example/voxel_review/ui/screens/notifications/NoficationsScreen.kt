package com.example.voxel_review.ui.screens.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.notifications.components.NotificationsContent
import com.example.voxel_review.ui.screens.notifications.components.NotificationsTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

/**
 * Pantalla principal del centro de notificaciones.
 * Observa el estado emitido por el ViewModel y renderiza el fondo, la barra y el contenido.
 *
 * @param notificationsViewModel ViewModel que gestiona el estado de las notificaciones.
 * @param onBackClick Acción ejecutada al presionar el botón de regresar.
 * @param onNotificationClick Acción ejecutada al interactuar con una notificación.
 * @param modifier Modificador para el contenedor de la pantalla.
 */
@Composable
fun NotificationScreen(
    notificationsViewModel: NotificationsViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by notificationsViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            NotificationsTopBar(
                onBackClick = onBackClick
            )

            NotificationsContent(
                highlightedNotifications = state.highlightedNotifications,
                followSuggestions = state.followSuggestions,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable fun NotificationScreenPreview() {
    NotificationScreen(
        notificationsViewModel = viewModel(),
        onBackClick = {},
        onNotificationClick = {}
    )
}