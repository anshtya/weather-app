package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.runtime.Composable

@Composable
fun WeatherScreen(
    uiState: WeatherUiState,
    weatherId: String?,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onErrorShown: () -> Unit,
    onUpdateClick: () -> Unit,
) {
    WeatherDrawer(
        uiState = uiState,
        weatherId = weatherId,
        onSettingsClick = onSettingsClick,
        onManageLocationsClick = onManageLocationsClick,
        onErrorShown = onErrorShown,
        onUpdateClick = onUpdateClick
    )
}