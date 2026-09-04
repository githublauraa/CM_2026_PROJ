package com.example.voxel_review.ui.screens.Discover.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.onSurfaceDark
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.primaryDark
import com.example.voxel_review.ui.theme.tertiaryDark

/**
 * Campo de texto personalizado para realizar búsquedas dentro de la sección de descubrimiento.
 *
 * @param query Texto actual introducido en el buscador.
 * @param onQueryChange Callback que se ejecuta cuando el texto del buscador cambia.
 * @param modifier Modificador para personalizar la apariencia o disposición del componente.
 */
@Composable
fun DiscoverSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        placeholder = {
            Text(
                text = "Buscar juegos, géneros...",
                color = onSurfaceVariantDark,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "icono buscador",
                tint = onSurfaceVariantDark,
                modifier = Modifier.size(20.dp)
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = primaryDark,
            unfocusedContainerColor = primaryDark,
            disabledContainerColor = primaryDark,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = onSurfaceDark,
            unfocusedTextColor = onSurfaceDark,
            cursorColor = tertiaryDark,

    )
    )
}

@Preview(showBackground = true)
@Composable
private fun DiscoverSearchBarPreview() {
    DiscoverSearchBar(query = "", onQueryChange = {})
}
