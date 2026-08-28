package com.example.voxel_review.ui.screens.rankings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.infoRanking.ListaRanking
import com.example.voxel_review.data.infoRanking.RankingUsuario
import com.example.voxel_review.ui.screens.rankings.components.BotonScreen
import com.example.voxel_review.ui.screens.rankings.components.Estrella
import com.example.voxel_review.ui.screens.rankings.components.MensajeSuperior
import com.example.voxel_review.ui.screens.rankings.components.RankingCard
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.DiscoverTab
import com.example.voxel_review.ui.utils.FondoPantalla


@Composable
fun RankingContent(
    rankingUsuarios: List<RankingUsuario>,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        MensajeSuperior(
            modifier = Modifier.align(Alignment.TopStart)
        )

        Estrella(
            modifier = Modifier.align(Alignment.TopEnd)
        )

        BotonScreen(
            textoBoton = "Críticos",
            colorFondo = VoxelPrimary,
            colorTexto = Color.Gray,
            colorBorde = VoxelPrimary,
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 70.dp,
                    start = 18.dp
                )
        )

        BotonScreen(
            textoBoton = "Juegos",
            colorFondo = VoxelBackground,
            colorTexto = Color.Gray,
            colorBorde = Color.Gray,
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 70.dp,
                    start = 125.dp
                )
        )

        Text(
            text = "TOP CRÍTICOS",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 140.dp,
                    start = 18.dp
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 175.dp,
                    start = 18.dp,
                    end = 18.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(rankingUsuarios) { usuario ->

                RankingCard(
                    posicion = usuario.posicion,
                    nombre = usuario.nombre,
                    reseñas = usuario.reseñas,
                    porcentaje = usuario.porcentaje
                )
            }
        }
    }
}


@Composable
fun RankingsScreen(
    rankingUsuarios: List<RankingUsuario>,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla()

        RankingContent(
            rankingUsuarios = rankingUsuarios,
            modifier = Modifier.padding(bottom = 70.dp)
        )

        DiscoverBottomNavigationBar(
            selectedTab = DiscoverTab.HOME,
            onTabSelected = {},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {

    RankingsScreen(
        rankingUsuarios = ListaRanking.rankingUsuarios
    )
}