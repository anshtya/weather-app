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
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchLocations by viewModel.searchLocations.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.isLocationAdded == true) {
            onNavigateToWeatherScreen()
        }
    }

    AddLocationScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        searchLocations = searchLocations,
        onBackClick = onBackClick,
        onSearchTextChange = { viewModel.onSearchQueryChange(it) },
        onLocationClick = { viewModel.onLocationClick(it) },
        onAddCurrentLocationClick = { viewModel.getUserCurrentLocation() },
        onErrorShown = { viewModel.errorShown() }
    )
}