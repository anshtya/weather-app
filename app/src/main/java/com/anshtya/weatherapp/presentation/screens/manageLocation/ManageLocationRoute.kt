package com.anshtya.weatherapp.presentation.screens.manageLocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ManageLocationRoute(
    onBackClick: () -> Unit,
    onAddLocationClick: () -> Unit,
//    onNavigateToAddLocationScreen: () -> Unit,
    viewModel: ManageLocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ManageLocationScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddLocationClick = onAddLocationClick,
        onDeleteLocation = { viewModel.deleteLocation(it) }
    )
}