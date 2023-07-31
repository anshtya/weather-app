package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WeatherRoute(
    weatherId: String?,
    onNavigateToAddLocationScreen: () -> Unit,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.hasLocations.collect {
            if (!it) {
                onNavigateToAddLocationScreen()
            }
        }
    }

    WeatherScreen(
        uiState = uiState,
        weatherId = weatherId,
        onManageLocationsClick = onManageLocationsClick,
        onSettingsClick = onSettingsClick,
        onErrorShown = { viewModel.errorShown() },
        onUpdateClick = { viewModel.updateWeather() }
    )
}