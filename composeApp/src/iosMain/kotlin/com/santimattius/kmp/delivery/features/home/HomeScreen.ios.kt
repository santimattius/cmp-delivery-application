package com.santimattius.kmp.delivery.features.home

import LocalNativeViewFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitViewController
import com.santimattius.kmp.delivery.core.domain.Vendor
import kotlinx.cinterop.ExperimentalForeignApi

@Composable
actual fun NativeVendorItem(vendor: Vendor) {
    val nativeViewFactory = LocalNativeViewFactory.current
    UIKitViewController(
        factory = { nativeViewFactory.createVendorRow(vendor) },
        modifier = Modifier.height(100.dp).fillMaxWidth(),
        update = {},
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}