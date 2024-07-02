package com.santimattius.kmp.delivery.features.map

import LocalNativeViewFactory
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.unit.dp
import com.santimattius.kmp.delivery.core.domain.Vendor
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit,
    modifier: Modifier
) {

    val nativeViewFactory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Black),
        factory = { nativeViewFactory.createMapView(vendors, onItemClick) },
        update = {}
    )
}