package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.profile.components.EditButton
import com.example.voxel_review.ui.screens.profile.components.Location
import com.example.voxel_review.ui.screens.profile.components.Pfp
import com.example.voxel_review.ui.screens.profile.components.GameCards
import com.example.voxel_review.ui.screens.profile.components.StatsPanel
import com.example.voxel_review.ui.screens.profile.components.TopBar
import com.example.voxel_review.ui.screens.profile.components.UserNick
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier
){
    Box(
	modifier = modifier.fillMaxSize()
    ){
	Column(
        horizontalAlignment = Alignment.CenterHorizontally,
	    modifier = Modifier.align(Alignment.TopCenter)
    ){
        TopBar()
        Pfp()
        UserNick()
        Location()
        StatsPanel()
        GameCards()
        EditButton()
    }
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        FondoPantalla()
        ProfileContent(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 20.dp)//para mover 20dp hacia abajo
        )
    }
}
