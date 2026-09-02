package com.example.voxel_review.ui.screens.home.components

import androidx.compose.runtime.Composable
import com.example.voxel_review.ui.utils.Boton
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment

import com.example.voxel_review.ui.theme.tertiaryDark
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Spacer
import com.example.voxel_review.ui.theme.surfaceContainerLowestLight
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import com.example.voxel_review.ui.theme.onTertiaryLight
import com.example.voxel_review.ui.theme.backgroundDark
import com.example.voxel_review.ui.theme.secondaryDark

@Composable
fun LoginButtons(
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp)
    ) {

        Boton(
            textoBoton = "iniciar sesión",
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