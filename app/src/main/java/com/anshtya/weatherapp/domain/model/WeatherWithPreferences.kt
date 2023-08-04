package com.anshtya.weatherapp.domain.model

/*
* data class for storing list of weather and preference
*/
data class WeatherWithPreferences(
    val weather: List<Weather> = emptyList(),
    val showCelsius: Boolean = false
)
