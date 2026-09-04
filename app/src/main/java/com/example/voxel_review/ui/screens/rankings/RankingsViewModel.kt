package com.example.voxel_review.ui.screens.rankings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.voxel_review.data.infoRanking.ListaRanking

class RankingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RankingsState())
    val uiState: StateFlow<RankingsState> = _uiState.asStateFlow()

    fun getAllRankings() {
        _uiState.value = _uiState.value.copy(
            rankingsUser = ListaRanking.rankingUsuarios
        )
    }

    fun onChangeSeleccionado(seleccionado: Boolean) {
        _uiState.value = _uiState.value.copy(
            seleccionado = seleccionado
        )
    }

    init {
        getAllRankings()
    }
}