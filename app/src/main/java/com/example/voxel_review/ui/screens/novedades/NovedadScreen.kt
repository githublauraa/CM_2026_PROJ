package com.example.voxel_review.ui.screens.novedades

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.ui.screens.novedades.components.NovedadesContent
import com.example.voxel_review.ui.utils.FondoPantalla

/**
 * Contenido visual de la pantalla de novedades.
 * Recibe el estado y los eventos sin acceder directamente al ViewModel.
 *
 * @param state Estado actual de la pantalla de novedades.
 * @param onCategoriaSeleccionada Acción ejecutada al seleccionar una categoría.
 * @param onClick Acción ejecutada al seleccionar un juego.
 * @param onNotificationClick Acción ejecutada al presionar el icono de notificaciones.
 * @param modifier Modificador para personalizar el contenido.
 */
@Composable
fun NovedadBody(
    state: NovedadesState,
    onCategoriaSeleccionada: (String) -> Unit,
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NovedadesContent(
            state = state,
            onClick = onClick,
            onNotificationClick = onNotificationClick,
            onCategoriaSeleccionada = onCategoriaSeleccionada
        )
    }
}

/**
 * Pantalla de novedades conectada al ViewModel.
 * Observa el estado y conecta los eventos de la interfaz con la lógica correspondiente.
 *
 * @param novedadesViewModel ViewModel encargado del estado y la lógica de novedades.
 * @param onClick Acción ejecutada al seleccionar un juego.
 * @param onNotificationClick Acción ejecutada al acceder a las notificaciones.
 * @param modifier Modificador para personalizar la pantalla.
 */
@Composable
fun NovedadScreen(
    novedadesViewModel: NovedadesViewModel,
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Convierte el StateFlow del ViewModel en un estado observable por Compose.
    val state by novedadesViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        NovedadBody(
            state = state,
            onCategoriaSeleccionada = {
                novedadesViewModel.updateSelectedCategory(it)
            },
            onClick = onClick,
            onNotificationClick = onNotificationClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Vista previa de la pantalla de novedades.
 */
@Composable
@Preview(showBackground = true)
fun NovedadScreenPreview() {
    NovedadScreen(
        novedadesViewModel = viewModel(),
        onClick = {},
        onNotificationClick = {}
    )
}