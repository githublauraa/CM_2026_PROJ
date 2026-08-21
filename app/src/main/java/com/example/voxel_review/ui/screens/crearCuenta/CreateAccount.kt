package com.example.voxel_review.ui.screens.crearCuenta

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AccountContent(
    modifier: Modifier = Modifier
){
    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

}

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier
){

}