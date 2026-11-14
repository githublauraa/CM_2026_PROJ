package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.data.info.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.theme.onSurfaceDark
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.secondaryDark
import com.example.voxel_review.ui.utils.GameImage

/**
 * Elemento de lista que muestra la información de un elemento en tendencia de búsqueda.
 *
 * @param item Información de la tendencia de búsqueda a mostrar.
 * @param onClick Acción ejecutada al hacer clic sobre el elemento.
 * @param modifier Modificador para personalizar la tarjeta o contenedor del elemento.
 */
@Composable
fun TrendingSearchItem(
    item: TrendingSearchInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GameImage(
            imageUrl = item.imagen,
            contentDescription = null,
            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.nombre,
                color = onSurfaceDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.categoria,
                color = onSurfaceVariantDark,
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            painter = painterResource(R.drawable.northeast_arrow),
            contentDescription = null,
            tint = secondaryDark,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrendingSearchItemPreview() {
    TrendingSearchItem(
        item = TrendingSearchInfo(
            id = "1",
            nombre = "Elden Ring",
            categoria = "RPG de acción",
            imagen = ""
        ),
        onClick = {}
    )
}
