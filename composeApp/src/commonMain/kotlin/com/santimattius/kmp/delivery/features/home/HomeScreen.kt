package com.santimattius.kmp.delivery.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.delivery.core.domain.Vendor
import com.santimattius.kmp.delivery.core.ui.components.CircularAvatar
import com.santimattius.kmp.delivery.core.ui.components.ErrorView
import com.santimattius.kmp.delivery.core.ui.components.LoadingIndicator
import com.santimattius.kmp.delivery.core.ui.components.NetworkImage
import com.santimattius.kmp.delivery.core.ui.components.SearchAppBar
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    screenModel: HomeScreenViewModel = koinViewModel(),
    onOpenMap: () -> Unit = {},
) {

    Scaffold(
        topBar = { SearchAppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenMap) {
                Icon(Icons.Default.Map, contentDescription = null)
            }
        }
    ) {
        val state by screenModel.state.collectAsStateWithLifecycle()
        Box(
            modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> LoadingIndicator()

                state.hasError -> {
                    ErrorView(message = "Something went wrong")
                }

                else -> {
                    VendorList(total = state.total, vendors = state.data)
                }
            }
        }
    }
}

@Composable
private fun VendorList(
    total: Long,
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var isCard by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        modifier = modifier
    ) {
        item {
            VendorHeader(
                total = total,
                isCard = isCard,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth(),
                onViewTypeClicked = { isCard = !isCard },
            )
        }
        itemsIndexed(vendors, key = { _, item -> item.id }) { index, vendor ->
            if (isCard) {
                VendorRowCard(
                    modifier = Modifier.padding(
                        vertical = 4.dp,
                    ),
                    elevation = 2.dp,
                    vendor = vendor
                )
            } else {
                VendorRowItem(
                    vendor = vendor,
                    onItemClick = onItemClick,
                )
                if (index < vendors.lastIndex) {
                    HorizontalDivider(
                        color = LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(
                            top = 8.dp, bottom = 8.dp, start = 16.dp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun VendorHeader(
    modifier: Modifier = Modifier,
    total: Long,
    isCard: Boolean,
    onViewTypeClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$total restaurantes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onViewTypeClicked,
            modifier = Modifier
                .weight(0.1f)
        ) {
            Icon(
                imageVector = if (!isCard) Icons.Filled.GridView else Icons.AutoMirrored.Filled.ViewList,
                contentDescription = if (!isCard) "Change to Card" else "Change to Simple",
            )
        }
    }
}

@Composable
fun VendorRowCard(
    vendor: Vendor,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column {
            NetworkImage(
                imageUrl = vendor.headerImage,
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
                    .background(LightGray.copy(alpha = 0.3f))
                    .aspectRatio(ratio = (16 / 8).toFloat()),
            )
            VendorRowItem(vendor)
        }
    }
}


@Composable
internal fun VendorRowItem(
    vendor: Vendor,
    onItemClick: (Vendor) -> Unit = {},
) {
    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier.clickable { onItemClick(vendor) },
        leadingContent = {
            CircularAvatar(
                image = vendor.logo, contentDescription = vendor.name, size = 60.dp
            )
        },
        headlineContent = {
            Text(
                text = vendor.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Column(
                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top
            ) {
                if (vendor.categories.isNotEmpty()) {
                    Text(text = vendor.categories.joinToString("·"), fontSize = 12.sp)
                }
                Text(text = "${vendor.deliveryTime}·Envio ${vendor.deliveryFee}", fontSize = 12.sp)
                Spacer(modifier = Modifier.fillMaxHeight())
            }
        },
        trailingContent = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color(0xfff86601),
                    modifier = Modifier.size(16.dp)
                )
                Text(text = vendor.rating.toString(), fontSize = 12.sp)
            }
        }
    )
}

@Composable
expect fun NativeVendorItem(vendor: Vendor)