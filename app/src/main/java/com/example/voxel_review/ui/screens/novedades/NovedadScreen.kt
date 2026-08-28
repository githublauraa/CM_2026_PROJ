package com.example.voxel_review.ui.screens.novedades

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.data.LocalJuegosProvider
import com.example.voxel_review.ui.screens.novedades.components.NovedadesContent
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.DiscoverTab
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
@Composable
fun NovedadBody(
    listaJuegos: List<JuegoInfo>,
    modifier: Modifier = Modifier
) {


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NovedadesContent(
            listaJuegos = listaJuegos
        )
    }
}

@Composable
fun NovedadScreen(
    listaJuegos: List<JuegoInfo>,
    modifier: Modifier = Modifier
) {
    Box(

    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        NovedadBody(
            listaJuegos = listaJuegos,
            modifier = Modifier.fillMaxSize()
        )

        DiscoverBottomNavigationBar(
            selectedTab = DiscoverTab.FAVORITES,
            onTabSelected = {},
            modifier = Modifier.align(Alignment.BottomCenter)
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
        listaJuegos = LocalJuegosProvider.juegos
    )
}