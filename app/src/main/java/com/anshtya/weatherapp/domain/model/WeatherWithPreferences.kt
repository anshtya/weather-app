package com.anshtya.weatherapp.domain.model

/*
* data class for storing list of weather and preference
*/
data class WeatherWithPreferences(
    val weather: List<Weather> = emptyList(),
    val savedLocations: List<SavedLocation> = emptyList(),
    val showCelsius: Boolean = true
)
