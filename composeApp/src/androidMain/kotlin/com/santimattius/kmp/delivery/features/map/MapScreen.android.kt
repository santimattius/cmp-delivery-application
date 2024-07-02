package com.santimattius.kmp.delivery.features.map

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.santimattius.kmp.delivery.core.domain.Vendor

@Composable
actual fun MapView(
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Text("Map")
    }
}