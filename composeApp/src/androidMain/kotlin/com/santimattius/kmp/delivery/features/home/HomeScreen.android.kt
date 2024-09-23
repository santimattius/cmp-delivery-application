package com.santimattius.kmp.delivery.features.home

import androidx.compose.runtime.Composable
import com.santimattius.kmp.delivery.core.domain.Vendor

@Composable
actual fun NativeVendorItem(vendor: Vendor) {
    VendorRowItem(vendor = vendor)
}