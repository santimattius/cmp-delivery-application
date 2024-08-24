package com.santimattius.kmp.delivery.core.data.sources.ktor

import com.santimattius.kmp.delivery.core.data.VendorDto
import com.santimattius.kmp.delivery.core.data.asDomains
import com.santimattius.kmp.delivery.core.data.sources.VendorRemoteDataSource
import com.santimattius.kmp.delivery.core.domain.VendorResult
import com.santimattius.kmp.entertainment.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorVendorRemoteDataSource(
    private val client: HttpClient
) : VendorRemoteDataSource {
    override suspend fun getVendors(): Result<VendorResult> = runCatching {
        val response = client.get("/restaurants") {
            headers {
                append(AVOCODE, BuildConfig.apiKey)
            }
        }
        val body = response.body<List<VendorDto>>()
        VendorResult(
            total = body.size.toLong(),
            vendors = body.asDomains()
        )
    }
    companion object{
        private const val AVOCODE = "avocode"
    }
}