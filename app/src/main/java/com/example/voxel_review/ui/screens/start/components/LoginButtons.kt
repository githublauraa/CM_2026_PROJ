package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.backgroundDark
import com.example.voxel_review.ui.theme.onTertiaryLight
import com.example.voxel_review.ui.theme.secondaryDark
import com.example.voxel_review.ui.theme.surfaceContainerLowestLight
import com.example.voxel_review.ui.theme.tertiaryDark
import com.example.voxel_review.ui.utils.Boton

/**
 * Muestra las opciones principales de acceso a la aplicación.
 * Incluye los botones para iniciar sesión y crear una cuenta.
 *
 * @param logginButtonPressed Acción ejecutada al presionar el botón de inicio de sesión.
 * @param createAccountButtonPressed Acción ejecutada al presionar el botón de creación de cuenta.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun LoginButtons(
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp)
    ) {
        if (isLoading) com.example.voxel_review.ui.auth.AuthLoading()
        Boton(
            textoBoton = if (isLoading) "Iniciando sesión…" else "Iniciar sesión",
            enabled = !isLoading,
            colorFondo = tertiaryDark,
            colorBorde = tertiaryDark,
            colorLetra = surfaceContainerLowestLight,
            onClick = logginButtonPressed
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LineaDivisora()

            Text(
                text = "o",
                color = onTertiaryLight
            )

            LineaDivisora()
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Boton(
            textoBoton = "Crear cuenta",
            enabled = !isLoading,
            colorFondo = backgroundDark,
            colorBorde = secondaryDark,
            colorLetra = secondaryDark,
            onClick = createAccountButtonPressed
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        MensajeTerminos()
    }
}