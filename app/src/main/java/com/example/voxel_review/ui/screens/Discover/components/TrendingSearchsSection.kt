package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.White
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import android.util.Log
/**
 * Secciona vertical que agrupa y despliega la lista de búsquedas en tendencia.
 *
 * @param items Lista de elementos en tendencia de búsqueda que se van a renderizar.
 * @param onItemClick Acción ejecutada al presionar un elemento en tendencia específico.
 * @param modifier Modificador para personalizar el contenedor de la sección.
 */
@Composable
fun TrendingSearchesSection(
    items: List<TrendingSearchInfo>,
    onItemClick: (TrendingSearchInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.TrendingUp,
                contentDescription = null,
                tint = VoxelPrimary,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "En tendencia",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEach { item ->
                TrendingSearchItem(
                    item = item,
                    onClick = {
                        Log.d("GAME_TEST", "ID del videojuego: '${item.id}'")
                        onItemClick(item)
                    }
                )
            }
        }
    }
}

@Preview()
@Composable
private fun TrendingSearchesSectionPreview() {
    /*
    TrendingSearchesSection(
        items = LocalTrendingSearchProvider.tendencias,
        onItemClick = { /*pantalla detalle juego*/
        }
    )

     */
}

