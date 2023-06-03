package com.anshtya.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshtya.weatherapp.presentation.MainActivity
import com.anshtya.weatherapp.presentation.screens.addLocation.AddLocationRoute
import com.anshtya.weatherapp.presentation.screens.manageLocation.ManageLocationScreen
import com.anshtya.weatherapp.presentation.screens.settings.SettingsRoute
import com.anshtya.weatherapp.presentation.screens.weather.WeatherRoute

@Composable
fun WeatherNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Weather.route
    ) {
        composable(route = Destinations.AddLocation.route) {
            val context = LocalContext.current as MainActivity
            AddLocationRoute(
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        context.onBackPressedDispatcher.onBackPressed()
                    }
                },
                onNavigateToWeatherScreen = {
                    if (navController.previousBackStackEntry != null) {
                        navController.navigate(Destinations.Weather.route) {
                            popUpTo(Destinations.Weather.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Destinations.Weather.route) {
                            popUpTo(Destinations.AddLocation.route) { inclusive = true }
                        }
                    }
                },
            )
        }
        composable(route = Destinations.Weather.route) {
            WeatherRoute(
                onNavigateToAddLocationScreen = {
                    navController.navigate(Destinations.AddLocation.route) {
                        popUpTo(Destinations.Weather.route) { inclusive = true }
                    }
                },
                onManageLocationsClick = { navController.navigate(Destinations.ManageLocation.route) },
                onSettingsClick = { navController.navigate(Destinations.Settings.route) },
            )
        }
        composable(route = Destinations.ManageLocation.route) {
            ManageLocationScreen(
                onAddLocationClick = {
                    navController.navigate(Destinations.AddLocation.route)
                }
            )
        }
        composable(route = Destinations.Settings.route) {
            SettingsRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}