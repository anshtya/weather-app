package com.anshtya.weatherapp.presentation.screens.manageLocation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageLocationScreen(
    uiState: ManageLocationUiState,
    onBackClick: () -> Unit,
    onAddLocationClick: () -> Unit,
    onDeleteLocation: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = onAddLocationClick
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                },
                title = { Text("Manage Locations") }
            )
        },
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(uiState.savedLocations.weatherList) { savedLocations ->
                    SavedLocationItem(
                        savedLocation = savedLocations,
                        showCelsius = uiState.savedLocations.showCelsius
                    )
                }
            }
        }
    }
}