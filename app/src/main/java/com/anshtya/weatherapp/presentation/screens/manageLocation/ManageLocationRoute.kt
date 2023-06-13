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
    onLocationClick: (String) -> Unit,
    onNavigateToAddLocationScreen: () -> Unit,
    viewModel: ManageLocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isTableNotEmpty = uiState.isTableNotEmpty
    LaunchedEffect(isTableNotEmpty) {
        if (isTableNotEmpty == false) {
            onNavigateToAddLocationScreen()
        }
    }

    ManageLocationScreen(
        savedLocations = uiState.savedLocations,
        isItemDeleted = uiState.isItemDeleted,
        errorMessage = uiState.errorMessage,
        onBackClick = onBackClick,
        selectLocation = { viewModel.selectLocation(it) },
        onAddLocationClick = onAddLocationClick,
        onLocationClick = onLocationClick,
        onDeleteLocation = { viewModel.deleteLocation() },
        onErrorShown = { viewModel.errorShown() }
    )
}