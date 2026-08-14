package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable

fun EditButton(
    modifier: Modifier = Modifier
){
    val colorFondo = colorResource(id = R.color.Voxel_elemento)

    Button(
	onClick = {},
    colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),
	shape = RoundedCornerShape(16.dp),
	modifier = modifier
    .padding(vertical = 16.dp)
	.border(
	    width = 2.dp,
	    color = colorResource(id = R.color.voxel_azul),
	    shape = RoundedCornerShape(16.dp),
    )
	.fillMaxWidth(0.9f)
    ) {
        Text(
	    text = "Editar Perfil",
	    color = colorResource( id = R.color.voxel_azul),
        fontWeight = FontWeight.Bold,
	)
    }
}
