package com.santimattius.kmp.delivery.core.data.sources

import com.santimattius.kmp.delivery.core.domain.Vendor
import com.santimattius.kmp.delivery.core.domain.VendorResult

interface VendorLocalDataSource {

    suspend fun getVendors(): Result<VendorResult>

    suspend fun save(vendors: List<Vendor>)
}
