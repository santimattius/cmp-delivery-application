package com.santimattius.kmp.delivery.features.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.santimattius.kmp.delivery.core.domain.Vendor
import com.santimattius.kmp.delivery.core.ui.components.CircularAvatar
import com.santimattius.kmp.delivery.core.ui.components.ErrorView
import com.santimattius.kmp.delivery.core.ui.components.LoadingIndicator
import com.santimattius.kmp.delivery.core.ui.components.NetworkImage
import com.santimattius.kmp.delivery.features.map.MapScreen

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        HomeScreenContent(screenModel) {
            navigator.push(MapScreen)
        }
    }
}

@Composable
fun HomeScreenContent(
    screenModel: HomeScreenModel,
    onOpenMap: () -> Unit = {},
) {
    val state by screenModel.state.collectAsState()

    Scaffold(
        topBar = { SearchAppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenMap) {
                Icon(Icons.Default.Map, contentDescription = null)
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> LoadingIndicator()

                state.hasError -> {
                    ErrorView(message = "An error occurred while updating the image")
                }

                else -> {
                    VendorList(total = state.total, vendors = state.data)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.weight(0.5f),
            imageVector = Icons.Default.Menu,
            contentDescription = ""
        )
        TextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            modifier = modifier.border(
                BorderStroke(width = 1.dp, color = LightGray),
                shape = RoundedCornerShape(20)
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = ""
                )
            },
            placeholder = {
                Text(text = "Buscar locales y platos")
            }
        )
        Icon(
            modifier = Modifier.weight(0.5f),
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = ""
        )
    }
}

@Composable
private fun VendorList(
    total: Long,
    vendors: List<Vendor>,
    onItemClick: (Vendor) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isGrid by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        modifier = modifier
    ) {
        item {
            Row(
                modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$total restaurantes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    isGrid = !isGrid
                }, modifier = Modifier.weight(0.1f)) {
                    Icon(
                        imageVector = if (!isGrid) Icons.Filled.GridView else Icons.AutoMirrored.Filled.ViewList,
                        contentDescription = "",
                    )
                }
            }
        }
        itemsIndexed(vendors, key = { _, item -> item.id }) { index, vendor ->
            if (isGrid) {
                VendorRowCard(vendor)
            } else {
                VendorRowItem(
                    vendor = vendor,
                    onItemClick = onItemClick,
                )
            }
            if (index < vendors.lastIndex)
                HorizontalDivider(
                    color = LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
        }
    }
}




@Composable
fun VendorRowCard(vendor: Vendor, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Column {
            NetworkImage(
                imageUrl = vendor.headerImage,
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGray)
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
                image = vendor.logo,
                contentDescription = vendor.name,
                size = 60.dp
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
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
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