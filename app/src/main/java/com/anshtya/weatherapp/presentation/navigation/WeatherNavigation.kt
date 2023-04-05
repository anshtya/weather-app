package com.anshtya.weatherapp.presentation.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshtya.weatherapp.presentation.MainActivity
import com.anshtya.weatherapp.presentation.screens.location.LocationViewModel
import com.anshtya.weatherapp.presentation.screens.location.SavedLocationScreen
import com.anshtya.weatherapp.presentation.screens.location.SelectLocationScreen
import com.anshtya.weatherapp.presentation.screens.weather.WeatherScreen

@Composable
fun WeatherNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current as MainActivity
    val locationViewModel = hiltViewModel<LocationViewModel>()
    val savedLocations by locationViewModel.savedLocations.collectAsStateWithLifecycle()
    val hasSavedLocation by remember { derivedStateOf { savedLocations.isNotEmpty() } }

    LaunchedEffect(hasSavedLocation) {
        if (!hasSavedLocation) {
            navController.navigate(SelectLocation.route) {
                popUpTo(Weather.routeWithArgs) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Weather.routeWithArgs
    ) {
        composable(route = SelectLocation.route) {
            val uiState by locationViewModel.uiState.collectAsStateWithLifecycle()
            SelectLocationScreen(
                uiState = uiState,
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        context.finish()
                    }
                },
                onTextChange = { text -> locationViewModel.onSearchTextChange(text) },
                onSubmit = { text -> locationViewModel.onSubmitSearch(text) },
                onLocationClick = { locationUrl ->
                    navController.navigate("${Weather.route}/$locationUrl") {
                        popUpTo(SelectLocation.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Weather.routeWithArgs) { navBackStackEntry ->
            WeatherScreen(
                locationUrl = navBackStackEntry.arguments?.getString(Weather.locationUrl)
            )
        }
        composable(route = SavedLocation.route) {
            SavedLocationScreen()
        }
    }
}