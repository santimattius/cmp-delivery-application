package com.santimattius.kmp.delivery.core.domain

data class Vendor(
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

data class Location(
    val lat: Double,
    val lng: Double
)

data class VendorResult(
    val total: Long,
    val vendors: List<Vendor>
)