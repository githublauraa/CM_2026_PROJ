package com.example.voxel_review.ui.screens.novedades

import android.app.Notification
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.data.LocalJuegosProvider
import com.example.voxel_review.ui.screens.novedades.components.NovedadesContent
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
@Composable
fun NovedadBody(
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick : () -> Unit,
    listaJuegos: List<JuegoInfo>,
    modifier: Modifier = Modifier
) {


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NovedadesContent(
            onClick = onClick,
            onNotificationClick = onNotificationClick,
            listaJuegos = listaJuegos
        )
    }
}

@Composable
fun NovedadScreen(
    onClick: (JuegoInfo) -> Unit,
    onNotificationClick: () -> Unit,
    listaJuegos: List<JuegoInfo>,
    modifier: Modifier = Modifier
) {
    Box(

    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        NovedadBody(
            onClick = onClick,
            onNotificationClick = onNotificationClick,
            listaJuegos = listaJuegos,
            modifier = Modifier.fillMaxSize()
        )

    }
}

@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
fun NovedadScreenPreview() {
    NovedadScreen(
        onClick = {},
        onNotificationClick = {},
        listaJuegos = LocalJuegosProvider.juegos
    )
}