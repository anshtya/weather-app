package com.anshtya.weatherapp.presentation.screens.manageLocation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ManageLocationScreen(
    onAddLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
//    val savedLocations by viewModel.savedLocations.collectAsStateWithLifecycle()

    Column(modifier) {
        Button(onClick = { onAddLocationClick() }) {
            Text("Add Location")
        }
    }
}