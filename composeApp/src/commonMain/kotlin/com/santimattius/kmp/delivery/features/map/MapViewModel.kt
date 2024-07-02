package com.santimattius.kmp.delivery.features.map

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.santimattius.kmp.delivery.core.data.VendorsRepository
import com.santimattius.kmp.delivery.core.domain.Vendor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: VendorsRepository
) : ScreenModel {
    private val _currentCoordinates = MutableStateFlow<List<Vendor>>(emptyList())
    val vendors = _currentCoordinates.asStateFlow()

    init {
        randomCoordinate()
    }
    private fun randomCoordinate() {
        screenModelScope.launch {
            repository.getVendors().onSuccess {
                _currentCoordinates.value = it.vendors
            }
        }
    }

}