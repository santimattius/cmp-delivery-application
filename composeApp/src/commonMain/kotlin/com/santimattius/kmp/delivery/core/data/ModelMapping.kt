package com.santimattius.kmp.delivery.core.data

import com.santimattius.kmp.delivery.core.domain.Location
import com.santimattius.kmp.delivery.core.data.VendorDto as VendorDto
import com.santimattius.kmp.delivery.core.domain.Vendor

fun List<VendorDto>.asDomains(): List<Vendor> {
    return map { it.asDomain() }
}

private fun VendorDto.asDomain(): Vendor {
    return Vendor(
        id = id,
        name = name,
        logo = "https://images.deliveryhero.io/image/pedidosya/restaurants/${logo}",
        deliveryTime = delivery.bucket.label,
        deliveryFee = delivery.price.deliveryFeeTotal,
        location = Location(location.latitude, location.longitude),
        rating = rating.score,
        isExclusive = isExclusive,
        isNew = isNew,
        headerImage = foodImage.url,
        categories = mainFoodCategories.map { it.name },
    )
}