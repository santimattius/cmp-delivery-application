package com.santimattius.kmp.delivery.features.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santimattius.kmp.delivery.core.domain.Vendor
import com.santimattius.kmp.delivery.core.ui.components.LottieLoader

@Composable
actual fun MapView(
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieLoader(
            resource = "files/avocado.json",
            contentDescription = "avocado"
        )
    }
}