package com.santimattius.kmp.delivery.core.data

import com.santimattius.kmp.delivery.core.data.sources.VendorLocalDataSource
import com.santimattius.kmp.delivery.core.data.sources.VendorRemoteDataSource
import com.santimattius.kmp.delivery.core.domain.VendorResult

class VendorsRepository(
    private val remoteDataSource: VendorRemoteDataSource,
    private val localDataSource: VendorLocalDataSource
) {

    suspend fun getVendors(): Result<VendorResult> {
       val result =  localDataSource.getVendors().fold(
            onSuccess = {
                if (it.vendors.isEmpty()) {
                    Result.failure(Throwable("no vendors"))
                } else {
                    Result.success(it)
                }
            },
            onFailure = { Result.failure(Throwable(it.message)) },
        )
        if (result.isSuccess) {
            return result
        }
        return remoteDataSource.getVendors().fold(
            onSuccess = {
                localDataSource.save(it.vendors)
                Result.success(it)
            },
            onFailure = { Result.failure(Throwable(it.message)) },
        )
    }
}


