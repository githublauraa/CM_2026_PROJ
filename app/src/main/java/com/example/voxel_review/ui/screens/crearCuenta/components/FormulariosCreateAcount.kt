package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountState
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.Boton
import com.example.voxel_review.ui.utils.CampoContrasena

/**
 * Formulario principal para el registro de un nuevo usuario.
 * Muestra los campos de usuario, correo, contraseña, términos y el botón de registro.
 *
 * @param state Estado actual de la pantalla de creación de cuenta.
 * @param onUserNameChange Acción ejecutada cuando cambia el nombre de usuario.
 * @param onEmailChange Acción ejecutada cuando cambia el correo electrónico.
 * @param onPasswordChange Acción ejecutada cuando cambia la contraseña.
 * @param onMostrarContrasenaChange Acción para mostrar u ocultar la contraseña.
 * @param onTerminosAceptadosChange Acción para cambiar la aceptación de los términos.
 * @param unirseButtonPressed Acción ejecutada al presionar el botón de registro.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun FomulariosCreateAcount(
    state: CreateAccountState,
    onUserNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    onTerminosAceptadosChange: () -> Unit,
    unirseButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .offset(y = 320.dp)
    ) {

        Text(
            text = "GAMER TAG / USERNAME",
            color = VoxelTextSecondary,
            modifier = Modifier.offset(x = (-60).dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormularioCreateAccount(
            value = state.userName,
            icono = R.drawable.img_username,
            placeholder = "username",
            onValueChange = onUserNameChange,
            modifier = Modifier
                .width(330.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CORREO ELECTRÓNICO",
            color = VoxelTextSecondary,
            modifier = Modifier.offset(x = (-70).dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormularioCreateAccount(
            value = state.email,
            icono = R.drawable.img_correo,
            placeholder = "correo electrónico",
            onValueChange = onEmailChange,
            modifier = Modifier
                .width(330.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CONTRASEÑA",
            color = VoxelTextSecondary,
            modifier = Modifier.offset(x = (-110).dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoContrasena(
            password = state.password,
            mostrarContrasena = state.mostrarContrasena,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,

            // Cambia el icono según el estado de visibilidad de la contraseña.
            icono = if (state.mostrarContrasena) {
                R.drawable.visible
            } else {
                R.drawable.img_no_ver_contrasena
            },

            modifier = Modifier
                .width(330.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // El mensaje solo se muestra cuando existe un error de validación.
        if (state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
                color = VoxelTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TerminosPrivacidad(
            aceptado = state.terminosAceptados,
            onAceptadoChange = {
                onTerminosAceptadosChange()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Boton(
            textoBoton = "Unirse a la aventura",
            modifier = Modifier
                .width(330.dp)
                .height(50.dp),
            colorFondo = VoxelSecondary,
            colorBorde = VoxelSecondary,
            colorLetra = White,
            onClick = unirseButtonPressed
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 20.dp)
        ) {

            // Permite aplicar diferentes estilos dentro de un mismo texto.
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = VoxelTextSecondary
                        )
                    ) {
                        append("¿Ya tienes una cuenta?")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = VoxelPrimary
                        )
                    ) {
                        append(" Iniciar sesión")
                    }
                },
                fontSize = 13.sp
            )
        }
    }
}