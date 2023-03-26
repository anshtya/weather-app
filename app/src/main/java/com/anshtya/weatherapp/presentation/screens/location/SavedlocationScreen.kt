package com.anshtya.weatherapp.presentation.screens.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SavedLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = hiltViewModel()
) {
//    val savedLocations by viewModel.savedLocations.collectAsStateWithLifecycle()

//    Column(modifier) {
//        for (location in savedLocations) {
//            Text(
//                text = location.name,
//                modifier = Modifier.padding(vertical = 10.dp)
//            )
//        }
//    }
}