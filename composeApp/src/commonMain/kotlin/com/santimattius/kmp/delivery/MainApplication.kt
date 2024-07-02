package com.santimattius.kmp.delivery

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.santimattius.kmp.delivery.di.applicationModules
import com.santimattius.kmp.delivery.features.splash.SplashScreen
import org.koin.compose.KoinApplication

@Composable
fun MainApplication() {
    KoinApplication(application = {
        modules(applicationModules())
    }) {
        Navigator(SplashScreen) {
            CurrentScreen()
        }
    }
}