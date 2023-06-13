package com.anshtya.weatherapp.presentation.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        showCelsius = uiState.showCelsius,
        onBackClick = onBackClick,
        onUnitSelect = { viewModel.showCelsius(it) }
    )
}