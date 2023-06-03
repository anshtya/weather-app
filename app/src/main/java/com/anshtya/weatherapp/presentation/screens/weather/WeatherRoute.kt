package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WeatherRoute(
    onNavigateToAddLocationScreen: () -> Unit,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.isTableEmpty.collect { isNotEmpty ->
            if (!isNotEmpty) {
                onNavigateToAddLocationScreen()
            }
        }
    }

    WeatherScreen(
        uiState = uiState,
        onChangeSelectedLocation = { viewModel.changeSelectedLocationId(it) },
        onManageLocationsClick = onManageLocationsClick,
        onSettingsClick = onSettingsClick,
        onErrorShown = { viewModel.errorShown() },
        onUpdate = { viewModel.sendUpdateWeatherOption(it) }
    )
}