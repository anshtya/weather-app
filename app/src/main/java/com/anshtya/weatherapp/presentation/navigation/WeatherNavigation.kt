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
import com.anshtya.weatherapp.presentation.screens.addLocation.AddLocationViewModel
import com.anshtya.weatherapp.presentation.screens.weather.SavedLocationScreen
import com.anshtya.weatherapp.presentation.screens.addLocation.SelectLocationScreen
import com.anshtya.weatherapp.presentation.screens.weather.WeatherScreen

@Composable
fun WeatherNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current as MainActivity
    val addLocationViewModel = hiltViewModel<AddLocationViewModel>()
    val isTableEmpty by addLocationViewModel.isTableEmpty.collectAsStateWithLifecycle()

    LaunchedEffect(isTableEmpty) {
        if (!isTableEmpty) {
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