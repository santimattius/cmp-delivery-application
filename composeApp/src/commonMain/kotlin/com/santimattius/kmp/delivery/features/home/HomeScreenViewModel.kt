package com.santimattius.kmp.delivery.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.delivery.core.data.VendorsRepository
import com.santimattius.kmp.delivery.core.domain.Vendor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Vendor> = emptyList(),
    val total: Long = 0,
)

class HomeScreenViewModel(
    private val repository: VendorsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state
        .onStart {
            loadVendors()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    private fun loadVendors() {
        _state.update { it.copy(isLoading = true, hasError = false) }
        viewModelScope.launch(exceptionHandler) {
            repository.getVendors().onSuccess { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = result.vendors,
                        total = result.total
                    )
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}