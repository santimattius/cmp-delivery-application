package com.santimattius.kmp.delivery.core.data.sources.ktor

import com.santimattius.kmp.delivery.core.data.VendorsResponse
import com.santimattius.kmp.delivery.core.data.asDomains
import com.santimattius.kmp.delivery.core.data.sources.RequestBuilder
import com.santimattius.kmp.delivery.core.data.sources.VendorRemoteDataSource
import com.santimattius.kmp.delivery.core.domain.VendorResult
import com.santimattius.kmp.entertainment.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorVendorRemoteDataSource(
    private val client: HttpClient
) : VendorRemoteDataSource {
    override suspend fun getVendors(): Result<VendorResult> = runCatching {
        val response = client.get("/vendors") {
            headers {
                append(AVOCODE, BuildConfig.apiKey)
            }
        }
        val body = response.body<VendorsResponse>()
        VendorResult(
            total = body.pagination.total,
            vendors = body.vendors.asDomains()
        )
    }
    companion object{
        private const val AVOCODE = "avocode"
    }
}