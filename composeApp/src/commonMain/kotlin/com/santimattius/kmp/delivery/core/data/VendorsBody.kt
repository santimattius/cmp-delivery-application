package com.santimattius.kmp.delivery.core.data

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json        = Json { allowStructuredMapKeys = true }
// val vendorsBody = json.parse(VendorsBody.serializer(), jsonString)
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class VendorsBody (
    val filters: List<String> = emptyList(),
    val businessTypes: List<String>,
    @SerialName("countryId")
    val countryID: Long,
    val point: Point,
    val sort: String,
    val offer: Offer
)

@Serializable
data class Offer (
    val occasions: List<String>
)

@Serializable
data class Point (
    val latitude: Double,
    val longitude: Double
)
