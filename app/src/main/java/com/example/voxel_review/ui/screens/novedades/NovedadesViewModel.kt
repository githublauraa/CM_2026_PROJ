package com.example.voxel_review.ui.screens.novedades

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.LocalJuegosProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de novedades.
 */
class NovedadesViewModel : ViewModel() {

    // Estado interno modificable únicamente desde el ViewModel.
    private val _uiState = MutableStateFlow(NovedadesState())

    // Estado público de solo lectura que puede observar la interfaz.
    val uiState: StateFlow<NovedadesState> = _uiState.asStateFlow()

    /**
     * Actualiza la categoría seleccionada por el usuario.
     *
     * @param category Nueva categoría seleccionada.
     */
    fun updateSelectedCategory(category: String) {
        _uiState.value = _uiState.value.copy(
            categoriaSeleccionada = category
        )
    }

    /**
     * Carga en el estado la lista de juegos disponibles.
     */
    fun getAllJuegos() {
        _uiState.value = _uiState.value.copy(
            listaJuegos = LocalJuegosProvider.juegos
        )
    }

    // Carga la lista inicial de juegos al crear el ViewModel.
    init {
        getAllJuegos()
    }
}