package com.santimattius.kmp.delivery.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VendorsResponse(
    @SerialName("vendors") val vendors: List<VendorDto>,
    @SerialName("metadata") val metadata: Metadata,
    @SerialName("pagination") val pagination: Pagination,
    @SerialName("aggregations") val aggregations: Aggregations
)

@Serializable
data class Aggregations(
    val discounts: Discounts,
    val zoneDelay: ZoneDelay
)

@Serializable
class Discounts()

@Serializable
data class ZoneDelay(
    val status: Status
)

@Serializable
enum class Status(val value: String) {
    @SerialName("NORMAL")
    Normal("NORMAL");
}

@Serializable
data class Metadata(
    @SerialName("midasRequestId")
    val midasRequestID: String,

    @SerialName("shoplistId")
    val shoplistID: String
)

@Serializable
data class Pagination(
    val offset: Long,
    val limit: Long,
    val size: Long,
    val total: Long
)

@Serializable
data class VendorDto(
    val id: Long,
    val name: String,
    val logo: String,
    val business: Business,
    val location: LocationDto,
    val delivery: VendorDelivery,
    val rating: Rating,
    val isFavourite: Boolean,
    val isNew: Boolean,
    val foodImage: FoodImage,
    val mainFoodCategories: List<MainFoodCategory>,
    val isExclusive: Boolean,
    val noMarkup: Boolean,
    val url: String
)

@Serializable
data class Business(
    val types: List<TypeElement>
)

@Serializable
enum class TypeElement(val value: String) {
    @SerialName("RESTAURANT")
    Restaurant("RESTAURANT");
}

@Serializable
data class VendorDelivery(
    val bucket: Bucket,
    val price: Price
)

@Serializable
data class Bucket(
    val lower: Long,
    val upper: Long,
    val label: String
)


@Serializable
data class Price(
    val deliveryFeeTotalWithDiscount: Double,
    val deliveryFeeTotal: Double,
    val fleetUtilisationLevel: Status,
    val deliveryFeeTotalMiddleEnd: Double
)

@Serializable
data class FoodImage(
    val url: String
)

@Serializable
data class LocationDto(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class MainFoodCategory(
    val id: Long,
    val name: String
)


@Serializable
data class Rating(
    val score: Double,
    val surveyCount: Long
)

