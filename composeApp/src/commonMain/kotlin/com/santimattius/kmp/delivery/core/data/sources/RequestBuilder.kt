package com.santimattius.kmp.delivery.core.data.sources

import com.santimattius.kmp.delivery.core.data.Offer
import com.santimattius.kmp.delivery.core.data.Point
import com.santimattius.kmp.delivery.core.data.VendorsBody

object RequestBuilder {

    fun makeDefaultBody(): VendorsBody {
        return VendorsBody(
            businessTypes = listOf("RESTAURANT"),
            countryID = 1,
            point = Point(-34.90111, -56.16453),
            sort = "RANKING",
            offer = Offer(occasions = listOf("DELIVERY", "PICKUP"))
        )
    }
}