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

    LaunchedEffect(Unit) {
        viewModel.isLocationAdded.collect{
            if(it) {
                onNavigateToWeatherScreen()
            }
        }
    }

    AddLocationScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSearchTextChange = { viewModel.onSearchTextChange(it) },
        onLocationClick = { viewModel.onLocationClick(it) },
        onAddCurrentLocationClick = { viewModel.getUserCurrentLocation() },
        onErrorShown = { viewModel.errorShown() }
    )
}