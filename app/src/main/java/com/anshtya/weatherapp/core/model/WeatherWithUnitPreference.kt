package com.anshtya.weatherapp.core.model

/*
* data class for storing list of weather and unit preference
*/
data class WeatherWithUnitPreference(
    val weatherList: List<Weather> = emptyList(),
    val showCelsius: Boolean = false,
)
