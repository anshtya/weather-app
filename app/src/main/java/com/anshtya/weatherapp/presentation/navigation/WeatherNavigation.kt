package com.anshtya.weatherapp.presentation.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshtya.weatherapp.presentation.MainActivity
import com.anshtya.weatherapp.presentation.screens.addLocation.AddLocationViewModel
import com.anshtya.weatherapp.presentation.screens.addLocation.SelectLocationScreen
import com.anshtya.weatherapp.presentation.screens.weather.ManageLocationScreen
import com.anshtya.weatherapp.presentation.screens.weather.WeatherScreen
import com.anshtya.weatherapp.presentation.screens.weather.WeatherViewModel
import kotlinx.coroutines.flow.first

@Composable
fun WeatherNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current as MainActivity
    val addLocationViewModel = hiltViewModel<AddLocationViewModel>()
    var isInitialAppLoad by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(isInitialAppLoad) {
        if (isInitialAppLoad) {
            addLocationViewModel.isTableNotEmpty.first {
                if (!it) {
                    navController.navigate(Destinations.SelectLocation.route) {
                        popUpTo(Destinations.Weather.route) { inclusive = true }
                    }
                }
                true
            }
            isInitialAppLoad = false
        }
    }

    NavHost(
        navController = navController,
        startDestination = Destinations.Weather.route
    ) {
        composable(route = Destinations.SelectLocation.route) {
            val uiState by addLocationViewModel.uiState.collectAsStateWithLifecycle()
            SelectLocationScreen(
                uiState = uiState,
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        context.finish()
                    }
                },
                onTextChange = { text -> addLocationViewModel.onSearchTextChange(text) },
                onSubmit = { text -> addLocationViewModel.onSubmitSearch(text) },
                onLocationClick = { locationUrl ->
                    addLocationViewModel.onLocationClick(locationUrl)
                    navController.navigate(Destinations.Weather.route) {
                        popUpTo(Destinations.SelectLocation.route) { inclusive = true }
                    }
                },
                onErrorShown = { addLocationViewModel.errorShown() }
            )
        }
        composable(route = Destinations.Weather.route) {
            val weatherViewModel = hiltViewModel<WeatherViewModel>()
            val uiState by weatherViewModel.uiState.collectAsStateWithLifecycle()
            WeatherScreen(
                uiState = uiState,
                onManageLocationsClick = {
                    navController.navigate(Destinations.ManageLocation.route)
                },
                onSettingsClick = {},
                onErrorShown = { weatherViewModel.errorShown() },
            )
        }
        composable(route = Destinations.ManageLocation.route) {
            ManageLocationScreen()
        }
    }
}