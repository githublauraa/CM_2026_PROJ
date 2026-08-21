package com.example.voxel_review.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSurface

@Composable
fun MostrarLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.logo_app),
        contentDescription = stringResource(R.string.logo_app),
        modifier = modifier
            .width(110.dp)
            .height(110.dp)
    )
}

@Composable
fun Boton(
    textoBoton: String,
    modifier: Modifier = Modifier,
    colorFondo: Color,
    colorBorde: Color,
    colorLetra: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .width(320.dp)
            .height(50.dp)
            .border(
                width = 2.dp,
                color = colorBorde,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = textoBoton,
            color = colorLetra
        )
    }
}

// Barra de navegacion
@Composable
fun BarraInferior(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp)
            .background(
                color = VoxelBackground
            )
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.ic_home),
            contentDescription = stringResource(R.string.inicio),
            modifier = Modifier.size(30.dp)
        )

        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.buscar),
            modifier = Modifier.size(30.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = stringResource(R.string.novedades),
                modifier = Modifier.size(32.dp)
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = stringResource(R.string.perfil),
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
@Preview
fun MostrarLogoPreview() {
    MostrarLogo()
}

@Composable
@Preview
fun BotonPreview() {
    Boton(
        textoBoton = "Iniciar sesion",
        colorFondo = VoxelSurface,
        colorBorde = VoxelSurface,
        colorLetra = VoxelSurface,
        onClick = {}
    )
}