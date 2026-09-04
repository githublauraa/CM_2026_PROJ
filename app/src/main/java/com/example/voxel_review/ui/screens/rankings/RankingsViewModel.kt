package com.example.voxel_review.ui.screens.rankings

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.infoRanking.ListaRanking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de rankings.
 */
class RankingsViewModel : ViewModel() {

    // Estado interno modificable únicamente desde el ViewModel.
    private val _uiState = MutableStateFlow(RankingsState())

    // Estado público de solo lectura que puede observar la interfaz.
    val uiState: StateFlow<RankingsState> = _uiState.asStateFlow()

    /**
     * Carga en el estado la lista de usuarios disponibles para el ranking.
     */
    fun getAllRankings() {
        _uiState.value = _uiState.value.copy(
            rankingsUser = ListaRanking.rankingUsuarios
        )
    }

    /**
     * Actualiza el tipo de ranking seleccionado.
     *
     * @param seleccionado `true` para mostrar críticos y `false` para mostrar juegos.
     */
    fun onChangeSeleccionado(seleccionado: Boolean) {
        _uiState.value = _uiState.value.copy(
            seleccionado = seleccionado
        )
    }

    // Carga la información inicial del ranking al crear el ViewModel.
    init {
        getAllRankings()
    }
}