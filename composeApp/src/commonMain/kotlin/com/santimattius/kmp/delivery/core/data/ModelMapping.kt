package com.santimattius.kmp.delivery.core.data

import com.santimattius.kmp.delivery.core.data.VendorDto
import com.santimattius.kmp.delivery.core.domain.Location
import com.santimattius.kmp.delivery.core.domain.Vendor

fun List<VendorDto>.asDomains(): List<Vendor> {
    return map { it.asDomain() }
}

private fun VendorDto.asDomain(): Vendor {
    return Vendor(
        id = id,
        name = name,
        logo = logo,
        deliveryTime = deliveryTime,
        deliveryFee = deliveryFee,
        location = Location(location.lat, location.lng),
        rating = rating,
        isExclusive = isExclusive,
        isNew = isNew,
        headerImage = headerImage,
        categories = categories,
    )
}