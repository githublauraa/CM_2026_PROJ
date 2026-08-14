package com.example.voxel_review.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.home.components.MensajeLogin
import com.example.voxel_review.ui.screens.home.components.NombreApp
import com.example.voxel_review.ui.screens.home.components.Subtitulo
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.utils.MostrarLogo



@Composable
fun HomeContent(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ){
            MostrarLogo()
            NombreApp()
            Subtitulo()
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 30.dp, //start es para el espacio que se quiere dejar desde la izquierda
                    bottom = 280.dp// espacio que se deja desde abajo
                )
        ){
            MensajeLogin()
        }
    }

}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        FondoPantalla()
        HomeContent(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen()
}


