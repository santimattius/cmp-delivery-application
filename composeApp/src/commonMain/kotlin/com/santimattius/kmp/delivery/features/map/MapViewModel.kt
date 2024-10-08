package com.santimattius.kmp.delivery.features.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.delivery.core.data.VendorsRepository
import com.santimattius.kmp.delivery.core.domain.Vendor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Vendor> = emptyList(),
)

class MapViewModel(
    private val repository: VendorsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MapUiState())
    val state = _state
        .onStart {
            loadVendors()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MapUiState(isLoading = true)
        )

    private fun loadVendors() {
        viewModelScope.launch {
            repository.getVendors().onSuccess { result ->
                _state.update {
                    it.copy(isLoading = false, data = result.vendors)
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }

}