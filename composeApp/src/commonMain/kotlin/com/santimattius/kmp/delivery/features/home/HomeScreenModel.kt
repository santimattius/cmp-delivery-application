package com.santimattius.kmp.delivery.features.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.santimattius.kmp.delivery.core.data.VendorsRepository
import com.santimattius.kmp.delivery.core.domain.Vendor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Vendor> = emptyList(),
    val total: Long = 0
)

class HomeScreenModel(
    private val repository: VendorsRepository,
) : StateScreenModel<HomeUiState>(HomeUiState()) {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        mutableState.update { it.copy(isLoading = false, hasError = true) }
    }

    init {
        randomImage()
    }

    private fun randomImage() {
        mutableState.update { it.copy(isLoading = true, hasError = false) }
        screenModelScope.launch(exceptionHandler) {
            repository.getVendors().onSuccess { result ->
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        data = result.vendors,
                        total = result.total
                    )
                }
            }.onFailure {
                mutableState.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}