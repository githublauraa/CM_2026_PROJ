package com.example.voxel_review.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.AdminAccessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AdminAccessViewModel @Inject constructor(private val access: AdminAccessRepository) : ViewModel() {
    private val _isAdmin = MutableStateFlow(false)
    val isAdmin = _isAdmin.asStateFlow()
    init {
        viewModelScope.launch {
            try { _isAdmin.value = access.isAdmin() }
            catch (e: CancellationException) { throw e }
            catch (e: Exception) { _isAdmin.value = false }
        }
    }
}
