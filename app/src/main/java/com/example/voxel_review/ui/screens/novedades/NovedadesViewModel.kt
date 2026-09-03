package com.example.voxel_review.ui.screens.novedades

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.voxel_review.data.LocalJuegosProvider
class NovedadesViewModel :  ViewModel(){
    private val _uiState = MutableStateFlow(NovedadesState())
    val uiState: StateFlow<NovedadesState> = _uiState.asStateFlow()

    fun updateSelectedCategory(category: String) {
        _uiState.value = _uiState.value.copy(categoriaSeleccionada = category)
    }

    fun getAllJuegos(){
        _uiState.value = _uiState.value.copy(listaJuegos = LocalJuegosProvider.juegos)
    }

    init {
        getAllJuegos()
    }

}