package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

/**
 * Barra superior para la pantalla de detalles que incluye botón de regreso, título y acceso a búsqueda.
 *
 * @param onBackPressed Acción ejecutada al presionar el botón de regreso.
 * @param onSearchPressed Acción ejecutada al presionar el botón de búsqueda.
 */
@Composable
fun GameDetailTopBar(
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBackPressed
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        Text(
            text = stringResource(R.string.detalle_juego),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        IconButton(
            onClick = onSearchPressed
        ) {
            Icon(
                painter = painterResource (R.drawable.ic_search),
                contentDescription = "Buscar",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun GameDetailTopBarPreview(){
    GameDetailTopBar(
        onBackPressed = {},
        onSearchPressed = {}
    )
}