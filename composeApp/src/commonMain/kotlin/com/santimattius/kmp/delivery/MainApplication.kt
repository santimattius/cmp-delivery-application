package com.santimattius.kmp.delivery

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santimattius.kmp.delivery.di.applicationModules
import com.santimattius.kmp.delivery.features.home.HomeScreen
import com.santimattius.kmp.delivery.features.map.MapScreen
import com.santimattius.kmp.delivery.features.splash.SplashScreen
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication

@Composable
fun MainApplication() {
    KoinApplication(application = {
        modules(applicationModules())
    }) {
        AppNavigation()
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen {
                navController.popBackStack()
                navController.navigate(Home)
            }
        }
        composable<Home> {
            HomeScreen {
                navController.navigate(Map)
            }
        }
        composable<Map> {
            MapScreen {
                navController.popBackStack()
            }
        }
    }


}

@Serializable
data object Splash

@Serializable
data object Home

@Serializable
data object Map