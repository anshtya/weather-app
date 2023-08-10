package com.anshtya.weatherapp.presentation.navigation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshtya.weatherapp.presentation.MainActivity
import com.anshtya.weatherapp.presentation.screens.addLocation.AddLocationRoute
import com.anshtya.weatherapp.presentation.screens.manageLocation.ManageLocationRoute
import com.anshtya.weatherapp.presentation.screens.settings.SettingsRoute
import com.anshtya.weatherapp.presentation.screens.weather.WeatherRoute

@Composable
fun WeatherNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Weather.routeWithArg,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(durationMillis = 200,easing = LinearOutSlowInEasing),
                initialOffsetX = { -it }
            )
        },
        exitTransition = {
            slideOutHorizontally(
                animationSpec = tween(durationMillis = 200,easing = LinearOutSlowInEasing),
                targetOffsetX = { it }
            )
        }
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
                        navController.popBackStack()
                    } else {
                        navController.navigateToWeatherScreen()
                    }
                },
            )
        }
        composable(
            route = Destinations.Weather.routeWithArg,
            arguments = Destinations.Weather.argument
        ) {
            WeatherRoute(
                weatherId = it.arguments?.getString(Destinations.Weather.argumentType),
                onNavigateToAddLocationScreen = { navController.navigateToAddLocationScreen() },
                onManageLocationsClick = { navController.navigate(Destinations.ManageLocation.route) },
                onSettingsClick = { navController.navigate(Destinations.Settings.route) },
            )
        }
        composable(route = Destinations.ManageLocation.route) {
            ManageLocationRoute(
                onBackClick = { navController.popBackStack() },
                onNavigateToAddLocationScreen = { navController.navigateToAddLocationScreen() },
                onAddLocationClick = { navController.navigate(Destinations.AddLocation.route) },
                onLocationClick = { navController.navigateToWeatherLocation(it) }
            )
        }
        composable(route = Destinations.Settings.route) {
            SettingsRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavController.navigateToAddLocationScreen() {
    navigate(Destinations.AddLocation.route) {
        popUpTo(Destinations.Weather.routeWithArg) { inclusive = true }
    }
}

fun NavController.navigateToWeatherScreen() {
    navigate(Destinations.Weather.routeWithArg) {
        popUpTo(Destinations.AddLocation.route) { inclusive = true }
    }
}

fun NavController.navigateToWeatherLocation(id: String) {
    navigate(Destinations.Weather.passId(id)) {
        popUpTo(Destinations.ManageLocation.route) { inclusive = true }
        launchSingleTop = true
    }
}