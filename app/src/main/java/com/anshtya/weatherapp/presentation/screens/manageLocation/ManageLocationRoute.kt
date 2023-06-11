package com.anshtya.weatherapp.presentation.screens.manageLocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ManageLocationRoute(
    onBackClick: () -> Unit,
    onAddLocationClick: () -> Unit,
    onNavigateToAddLocationScreen: () -> Unit,
    viewModel: ManageLocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isTableNotEmpty = uiState.isTableNotEmpty

    LaunchedEffect(isTableNotEmpty) {
        isTableNotEmpty?.let {
            if(!it) {
                onNavigateToAddLocationScreen()
            }
        }
    }

    ManageLocationScreen(
        savedLocations = uiState.savedLocations,
        isLoading = uiState.isLoading,
        onBackClick = onBackClick,
        selectLocation =  { viewModel.selectLocation(it) },
        onAddLocationClick = onAddLocationClick,
        onDeleteLocation = { viewModel.deleteLocation() }
    )
}