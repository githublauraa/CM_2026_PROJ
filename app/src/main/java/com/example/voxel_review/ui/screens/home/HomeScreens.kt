package com.example.voxel_review.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.home.components.CampoContrasena
import com.example.voxel_review.ui.screens.home.components.CampoUsuario
import com.example.voxel_review.ui.screens.home.components.MensajeLogin
import com.example.voxel_review.ui.screens.home.components.MensajeUsuario
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
    ){//columna para mostrar el logo, nombre y subtitulo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomStart)
                .padding(
                    start = 100.dp, //start es para el espacio que se quiere dejar desde la izquierda
                    bottom = 550.dp// espacio que se deja desde abajo
                )
        ){
            MostrarLogo()
            NombreApp()
            Subtitulo()
        }
        Column(//columna para mensaje de login/registro, mensaje USUARIO, PASSWORD y campos
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 30.dp, //start es para el espacio que se quiere dejar desde la izquierda
                    bottom = 300.dp// espacio que se deja desde abajo
                )
        ){
            MensajeLogin()
            Spacer(
                modifier = Modifier.height(10.dp) //height espacio entre composables
            )
            MensajeUsuario(
                tipo = "USUARIO",
            )

            Spacer(
                modifier = Modifier.height(10.dp) //height espacio entre composables
            )

            CampoUsuario()

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            MensajeUsuario(
                tipo = "CONTRASEÑA",
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
            CampoContrasena()
        }
    }

}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize()
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


