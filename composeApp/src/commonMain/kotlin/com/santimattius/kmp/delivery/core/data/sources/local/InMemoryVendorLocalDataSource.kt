package com.santimattius.kmp.delivery.core.data.sources.local

import com.santimattius.kmp.delivery.core.data.sources.VendorLocalDataSource
import com.santimattius.kmp.delivery.core.domain.Vendor
import com.santimattius.kmp.delivery.core.domain.VendorResult
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryVendorLocalDataSource : VendorLocalDataSource {
    private val mutex = Mutex()
    private val vendors = mutableListOf<Vendor>()
    override suspend fun getVendors(): Result<VendorResult> {
        return mutex.withLock {
            Result.success(VendorResult(total = 0L, vendors = vendors))
        }
    }

    override suspend fun save(vendors: List<Vendor>) {
        mutex.withLock {
            this.vendors.clear()
            this.vendors.addAll(vendors)
        }
    }
}