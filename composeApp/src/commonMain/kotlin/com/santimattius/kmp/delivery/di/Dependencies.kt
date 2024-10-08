package com.santimattius.kmp.delivery.di

import com.santimattius.kmp.delivery.core.data.VendorsRepository
import com.santimattius.kmp.delivery.core.data.sources.VendorLocalDataSource
import com.santimattius.kmp.delivery.core.data.sources.VendorMockRemoteDataSource
import com.santimattius.kmp.delivery.core.data.sources.VendorRemoteDataSource
import com.santimattius.kmp.delivery.core.data.sources.ktor.KtorVendorRemoteDataSource
import com.santimattius.kmp.delivery.core.data.sources.local.InMemoryVendorLocalDataSource
import com.santimattius.kmp.delivery.core.network.ktorHttpClient
import com.santimattius.kmp.delivery.features.home.HomeScreenViewModel
import com.santimattius.kmp.delivery.features.map.MapViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val sharedModules = module {
    single(qualifier(AppQualifiers.BaseUrl)) { "https://ts-mock-api.onrender.com" }
    single(qualifier(AppQualifiers.Client)) {
        ktorHttpClient(
            baseUrl = get(
                qualifier = qualifier(
                    AppQualifiers.BaseUrl
                )
            )
        )
    }

    single<VendorRemoteDataSource>(named(AppQualifiers.RemoteSource)) {
        KtorVendorRemoteDataSource(
            get(qualifier(AppQualifiers.Client))
        )
    }

    single<VendorRemoteDataSource>(named(AppQualifiers.FileSource)) {
        VendorMockRemoteDataSource()
    }
    single<VendorLocalDataSource> { InMemoryVendorLocalDataSource() }
    single { VendorsRepository(get(named(AppQualifiers.FileSource)), get()) }
}

val homeModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::MapViewModel)
}


fun applicationModules() = listOf(sharedModules, homeModule)