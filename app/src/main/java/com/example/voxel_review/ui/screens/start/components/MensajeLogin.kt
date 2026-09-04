package com.example.voxel_review.ui.screens.start.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

/**
 * Muestra el título del formulario de inicio de sesión.
 *
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun MensajeLogin(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.login_registro),
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = modifier
    )
}

/**
 * Vista previa del título de inicio de sesión.
 */
@Composable
@Preview
fun MensajeLoginPreview() {
    MensajeLogin()
}