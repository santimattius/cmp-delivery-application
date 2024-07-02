package com.santimattius.kmp.delivery.core.data.sources

import com.santimattius.kmp.delivery.core.domain.VendorResult

interface VendorRemoteDataSource {

    suspend fun getVendors(): Result<VendorResult>
}