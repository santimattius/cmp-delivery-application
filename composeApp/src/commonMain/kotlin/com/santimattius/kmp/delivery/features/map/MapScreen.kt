package com.santimattius.kmp.delivery.features.map

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cmp_delivery_application.composeapp.generated.resources.Res
import cmp_delivery_application.composeapp.generated.resources.app_name
import com.santimattius.kmp.delivery.core.domain.Vendor
import com.santimattius.kmp.delivery.core.ui.components.AppBar
import com.santimattius.kmp.delivery.features.home.VendorRowCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

object MapScreen : Screen {

    @Composable
    override fun Content() {
        MapScreenContent()
    }
}

@Composable
fun MapScreenContent(
    viewModel: MapViewModel = koinInject(),
    modifier: Modifier = Modifier
) {

    val navigator = LocalNavigator.currentOrThrow
    val vendors by viewModel.vendors.collectAsState()
    var select by remember { mutableStateOf<Vendor?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = stringResource(Res.string.app_name),
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = Color.Black,
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }
    ) {
        MapView(vendors = vendors, onItemClick = { select = it })
    }

    if (select != null) {
        BottomSheet(select!!) {
            select = null
        }
    }
}

@Composable
expect fun MapView(
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit,
    modifier: Modifier = Modifier
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(vendor: Vendor, onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        VendorRowCard(vendor)
    }
}