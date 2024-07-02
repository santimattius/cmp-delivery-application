package com.santimattius.kmp.delivery.di

import com.santimattius.kmp.delivery.core.data.VendorsRepository
import com.santimattius.kmp.delivery.core.data.sources.VendorLocalDataSource
import com.santimattius.kmp.delivery.core.data.sources.VendorRemoteDataSource
import com.santimattius.kmp.delivery.core.data.sources.ktor.KtorVendorRemoteDataSource
import com.santimattius.kmp.delivery.core.data.sources.local.InMemoryVendorLocalDataSource
import com.santimattius.kmp.delivery.core.network.ktorHttpClient
import com.santimattius.kmp.delivery.features.home.HomeScreenModel
import com.santimattius.kmp.delivery.features.map.MapViewModel
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
    single<VendorLocalDataSource> { InMemoryVendorLocalDataSource() }
    single { VendorsRepository(get(named(AppQualifiers.RemoteSource)), get()) }
}

val homeModule = module {
    factory { HomeScreenModel(repository = get()) }
    factory { MapViewModel(repository = get()) }
}


fun applicationModules() = listOf(sharedModules, homeModule)