package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.utils.BarraInferior
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
        BarraInferior(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
                .offset(y = 20.dp)
        )
    }
}
