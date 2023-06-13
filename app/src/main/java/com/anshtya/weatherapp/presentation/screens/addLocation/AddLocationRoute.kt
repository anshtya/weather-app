package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val isLocationAdded = uiState.isLocationAdded
    LaunchedEffect(isLocationAdded) {
        if(isLocationAdded == true) {
            onNavigateToWeatherScreen()
        }
    }

    AddLocationScreen(
        searchText = uiState.searchText,
        searchLocations = uiState.searchLocations,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onBackClick = onBackClick,
        onTextChange = { viewModel.onSearchTextChange(it) },
        onSubmit = { viewModel.onSubmitSearch(it) },
        onLocationClick = { viewModel.onLocationClick(it) },
        onAddCurrentLocationClick = { viewModel.getUserCurrentLocation() },
        onErrorShown = { viewModel.errorShown() }
    )
}