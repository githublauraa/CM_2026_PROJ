package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.screens.start.components.NombreApp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.utils.MostrarLogo

/**
 * Encabezado de la pantalla de creación de cuenta.
 * Muestra el logo, el nombre de la aplicación y el título de la pantalla.
 *
 * @param modifier Modificador para personalizar la distribución del componente.
 */

@Composable
fun CreateAcountHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MostrarLogo(
            modifier = Modifier.width(120.dp)
        )

        NombreApp()

        Spacer(modifier = Modifier.height(12.dp))

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
}
