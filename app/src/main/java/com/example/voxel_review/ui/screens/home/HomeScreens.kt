package com.example.voxel_review.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.home.components.CampoContrasena
import com.example.voxel_review.ui.screens.home.components.CampoUsuario
import com.example.voxel_review.ui.screens.home.components.LineaDivisora
import com.example.voxel_review.ui.screens.home.components.MensajeLogin
import com.example.voxel_review.ui.screens.home.components.MensajeTerminos
import com.example.voxel_review.ui.screens.home.components.MensajeUsuario
import com.example.voxel_review.ui.screens.home.components.NombreApp
import com.example.voxel_review.ui.screens.home.components.Subtitulo
import com.example.voxel_review.ui.utils.Boton
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
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 100.dp, //start es para el espacio que se quiere dejar desde la izquierda
                    bottom = 600.dp// espacio que se deja desde abajo
                )
        ){
            MostrarLogo()
            NombreApp()
            Subtitulo()
        }

        Column( //columna para mensaje de login/registro, mensaje USUARIO, PASSWORD y campos

            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 30.dp, //start es para el espacio que se quiere dejar desde la izquierda
                    bottom = 270.dp// espacio que se deja desde abajo
                )

        ){
            MensajeLogin()
            Spacer(
                modifier = Modifier.height(16.dp) //height espacio entre composables
            )
            MensajeUsuario(
                tipo = stringResource(R.string.usuario),
            )

            Spacer(
                modifier = Modifier.height(8.dp) //height espacio entre composables
            )

            CampoUsuario(
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            MensajeUsuario(
                tipo = stringResource(R.string.contrase_aa),
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
            CampoContrasena(
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier.height(115.dp)
            )
        }
        //columna para botones
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(
                    bottom = 180.dp
                )
        ) {
            Boton(
                stringResource(R.string.iniciar_sesi_n),
                colorFondo = colorResource(R.color.Voxel_rosado),
                colorBorde = colorResource(R.color.Voxel_rosado),
                colorLetra = colorResource(R.color.white)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                LineaDivisora()
                Text(
                    text = stringResource(R.string.o),
                    color = colorResource(R.color.Voxel_secundario)
                    )
                LineaDivisora()
            }
            Spacer(modifier = Modifier.height(10.dp))

            Boton(
                stringResource(R.string.crear_cuenta),
                colorFondo = colorResource(R.color.Voxel_morado_oscuro),
                colorBorde = colorResource(R.color.voxel_azul),
                colorLetra = colorResource(R.color.voxel_azul)
            )

            Spacer(modifier = Modifier.height(35.dp))

            MensajeTerminos()

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
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 20.dp)//para mover 20dp hacia abajo
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen()
}


