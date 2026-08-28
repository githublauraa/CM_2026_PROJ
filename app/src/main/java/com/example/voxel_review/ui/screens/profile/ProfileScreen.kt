package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier
) {

    val params = LocalProfileProvider.profiles[0]

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 70.dp)
        ) {
            TopBar()
            Pfp(params.pfp)
            UserNick(params.nick)
            Location()
            StatsPanel(params.resenias, params.promedio, params.likes)
            GameCards()
            EditButton()
        }
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        ProfileContent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ProfileScreen()
}
