package com.example.voxel_review.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.Boton
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.utils.MostrarLogo

@Composable
fun HomeContent(
    modifier: Modifier = Modifier
) {

    var usuario by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var mostrarContrasena by remember {
        mutableStateOf(false)
    }

    val icono =
        if (!mostrarContrasena) {
            R.drawable.img_no_ver_contrasena
        } else {
            R.drawable.visible
        }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // Columna para mostrar logo, nombre y subtitulo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {

            MostrarLogo()

            NombreApp()

            Subtitulo()
        }

        // Columna para formulario
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            MensajeLogin()

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            MensajeUsuario(
                tipo = stringResource(R.string.usuario)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            CampoUsuario(
                usuario = usuario,
                onUserChange = {
                    usuario = it
                },
                icono = R.drawable.imagen_login_user,
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            MensajeUsuario(
                tipo = stringResource(R.string.contrase_aa)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            CampoContrasena(
                password = password,
                onPasswordChange = {
                    password = it
                },
                mostrarContrasena = mostrarContrasena,
                onMostrarContrasenaChange = {
                    mostrarContrasena = !mostrarContrasena
                },
                icono = icono,
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            )

            if (password.isNotEmpty() && password.length < 6) {
                Text(
                    text = "La contraseña debe ser mayor a 6 caracteres",
                    color = White
                )
            }

            Spacer(
                modifier = Modifier.height(115.dp)
            )
        }

        // Columna para botones
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    bottom = 90.dp
                )
        ) {

            Boton(
                textoBoton = stringResource(R.string.iniciar_sesi_n),
                colorFondo = VoxelSecondary,
                colorBorde = VoxelSecondary,
                colorLetra = White,
                onClick = {
                    Log.d("HomeContent", "Iniciar sesion button")
                    Log.d("HomeContent", "tu_usuario es $usuario")
                    Log.d("HomeContent", "tu_contrasena es $password")
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                LineaDivisora()

                Text(
                    text = stringResource(R.string.o),
                    color = VoxelTextSecondary
                )

                LineaDivisora()
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Boton(
                textoBoton = stringResource(R.string.crear_cuenta),
                colorFondo = VoxelSurfaceVariant,
                colorBorde = VoxelPrimary,
                colorLetra = VoxelPrimary,
                onClick = {
                    Log.d("HomeContent", "Crear cuenta button")
                }
            )

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            MensajeTerminos()
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla()

        HomeContent(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 20.dp)
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}