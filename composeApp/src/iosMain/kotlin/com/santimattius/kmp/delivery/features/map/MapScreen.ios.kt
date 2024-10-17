package com.santimattius.kmp.delivery.features.map

import LocalNativeViewFactory
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitViewController
import com.santimattius.kmp.delivery.core.domain.Vendor

@Composable
actual fun MapView(
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit,
    modifier: Modifier
) {

    val nativeViewFactory = LocalNativeViewFactory.current
    UIKitViewController(
        factory = { nativeViewFactory.createMapView(vendors, onItemClick) },
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Black),
        update = {},
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}