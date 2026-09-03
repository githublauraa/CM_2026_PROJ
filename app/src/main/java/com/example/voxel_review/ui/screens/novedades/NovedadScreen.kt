package com.example.voxel_review.ui.screens.novedades

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.ui.screens.novedades.components.NovedadesContent
import com.example.voxel_review.ui.utils.FondoPantalla
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

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

@Composable
fun NovedadScreen(
    novedadesViewModel: NovedadesViewModel,
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {

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

@Composable
@Preview(showBackground = true)
fun NovedadScreenPreview(){
    NovedadScreen(
        novedadesViewModel = viewModel(),
        onClick = {},
        onNotificationClick = {}
    )
}