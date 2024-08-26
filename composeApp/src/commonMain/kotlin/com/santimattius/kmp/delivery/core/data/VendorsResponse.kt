package com.santimattius.kmp.delivery.core.data

import kotlinx.serialization.Serializable

@Serializable
data class VendorDto(
    val id: Long,
    val name: String,
    val logo: String,
    val location: Location,
    val isNew: Boolean,
    val isExclusive: Boolean,
    val deliveryTime: String,
    val deliveryFee: Double,
    val rating: Double,
    val headerImage: String,
    val categories: List<String>
) {
    val isFavorite: Boolean = false
}

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)