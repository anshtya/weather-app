package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.anshtya.weatherapp.domain.model.Weather

@Composable
fun WeatherScreen(
    uiState: WeatherState,
    modifier: Modifier = Modifier
) {
    val weatherLocations = uiState.weatherList

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        WeatherDrawer(
            weatherLocations = weatherLocations,
            content = { selectedWeatherLocationId ->
                WeatherDetails(
                    location = weatherLocations.first { it.id == selectedWeatherLocationId }
                )
            }
        )
    }
}

@Composable
fun WeatherDetails(
    location: Weather,
    modifier: Modifier = Modifier
) {
    Text(location.name)
}