package com.example.voxel_review.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun MostrarLogo(
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(R.drawable.logo_app),
        contentDescription = stringResource(R.string.logo_app),
        modifier = Modifier.width(110.dp).height(110.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun MostrarLogoPreview(){
    MostrarLogo()
}

@Composable
fun Boton(
    textoBoton: String,
    modifier: Modifier = Modifier,
    colorFondo: Color,
    colorBorde : Color,
    colorLetra: Color
){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),shape = RoundedCornerShape(10.dp),
        modifier = modifier.width(300.dp).height(50.dp).border(
            width = (2.dp),
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