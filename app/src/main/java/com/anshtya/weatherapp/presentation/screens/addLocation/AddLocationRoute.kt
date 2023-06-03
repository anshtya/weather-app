package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddLocationRoute(
    onBackClick: () -> Unit,
    onNavigateToWeatherScreen: () -> Unit,
    viewModel: AddLocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddLocationScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onNavigateToWeatherScreen = onNavigateToWeatherScreen,
        onTextChange = { viewModel.onSearchTextChange(it) },
        onSubmit = { viewModel.onSubmitSearch(it) },
        onLocationClick = { viewModel.onLocationClick(it) },
        onErrorShown = { viewModel.errorShown() }
    )
}