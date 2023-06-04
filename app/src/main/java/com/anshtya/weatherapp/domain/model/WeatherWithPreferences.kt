package com.anshtya.weatherapp.domain.model

/*
* data class for storing list of weather and preference
*/
data class WeatherWithPreferences(
    val weatherList: List<Weather> = emptyList(),
    val showCelsius: Boolean = false,
    val selectedLocationId: String = ""
)
