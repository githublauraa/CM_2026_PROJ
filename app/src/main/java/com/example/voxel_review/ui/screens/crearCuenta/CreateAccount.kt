package com.example.voxel_review.ui.screens.crearCuenta

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.utils.MostrarLogo
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.home.components.NombreApp
import com.example.voxel_review.ui.theme.VoxelPrimary
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import com.example.voxel_review.ui.theme.VoxelSecondary
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.screens.crearCuenta.components.FormularioCreateAccount
import com.example.voxel_review.ui.utils.CampoContrasena
import com.example.voxel_review.ui.theme.surfaceContainerLowestLight
import TerminosPrivacidad
import androidx.compose.foundation.layout.Row
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.Boton
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle

@Composable
fun CreateAccountContent(
    modifier: Modifier = Modifier
) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var terminosAceptados by remember {
        mutableStateOf(false)
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
        modifier = modifier.fillMaxWidth().offset(y =-67.dp)
    ){
        //columna para logo y nombre app
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            MostrarLogo(
                modifier = Modifier.width(120.dp).
                offset(y = 50.dp)
            )
            Spacer(
                modifier = Modifier.height(47.dp)
            )
            NombreApp(

            )
        }
        Spacer(
            modifier = Modifier.height(100.dp)
        )
        //column para mensaje crear cuenta voxel
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.offset(y = 210.dp).fillMaxWidth()
        )
        {
            Text(
                text = "Crear",
                color = VoxelPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Cuenta Voxel",
                color = VoxelSecondary,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().
            offset(y = 320.dp)
        ) {
            Text(
                text = "GAMER TAG / USERNAME",
                color = VoxelTextSecondary,
                modifier = Modifier.offset(x = -60.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            FormularioCreateAccount(
                userName = userName,
                icono = R.drawable.img_username,
                onUserChange = { userName = it },
                modifier = Modifier.width(330.dp).
                height(50.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "CORREO ELECTRÓNICO",
                color = VoxelTextSecondary,
                modifier = Modifier.offset(x = -70.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            FormularioCreateAccount(
                userName = email,
                icono = R.drawable.img_correo,
                onUserChange = { email = it },
                modifier = Modifier.width(330.dp).
                height(50.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "CONTRASEÑA",
                color = VoxelTextSecondary,
                modifier = Modifier.offset(x = -110.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            CampoContrasena(
                password = password,
                mostrarContrasena = mostrarContrasena,
                icono = icono,
                onPasswordChange = { password = it },
                onMostrarContrasenaChange = {
                    mostrarContrasena = !mostrarContrasena
                },
                modifier = Modifier
                    .width(330.dp)
                    .height(50.dp)
            )

            if (password.isNotEmpty() && password.length < 6) {
                Text(
                    text = stringResource(R.string.la_contrase_a_debe_ser_mayor_a_6_caracteres),
                    color = surfaceContainerLowestLight,
                    modifier = modifier.size(30.dp).
                    offset(x = 16.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            TerminosPrivacidad(
                aceptado = terminosAceptados,
                onAceptadoChange = { terminosAceptados = it }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Boton(
                textoBoton = "Unirse a la aventura",
                colorFondo = VoxelSecondary,
                colorBorde = VoxelSecondary,
                colorLetra = White,
                modifier = Modifier
                    .width(330.dp)
                    .height(50.dp),
                onClick = {}
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().
                offset(x = 20.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = VoxelTextSecondary
                            )
                        ){
                            append("¿Ya tienes una cuenta?")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = VoxelPrimary,
                            )
                        ){
                            append(" Iniciar sesión")
                        }
                    },
                    fontSize = 13.sp
                )
            }




        }


    }

}

@Composable
fun ButtonUniverAventura(
    modifier: Modifier = Modifier
){



}

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()){
        FondoPantalla()
        CreateAccountContent(
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview (showBackground = true)
@Composable
private fun CreateAccountScreenPreview() {
    CreateAccountScreen()
}

