package com.santimattius.kmp.delivery.core.data.sources

import cmp_delivery_application.composeapp.generated.resources.Res
import com.santimattius.kmp.delivery.core.data.VendorDto
import com.santimattius.kmp.delivery.core.data.asDomains
import com.santimattius.kmp.delivery.core.domain.VendorResult
import com.santimattius.kmp.delivery.core.network.decodeFromString
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi

class VendorMockRemoteDataSource : VendorRemoteDataSource {
    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getVendors(): Result<VendorResult> = runCatching {
        delay(1000L)
        val jsonStr = Res.readBytes("files/vendors.json").decodeToString()
        val response = decodeFromString<List<VendorDto>>(jsonStr)
        VendorResult(response.size.toLong(), response.asDomains())
    }
}