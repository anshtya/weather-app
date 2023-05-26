package com.anshtya.weatherapp.domain.model

import com.anshtya.weatherapp.core.model.Weather

/*
* data class for storing list of weather and preference
*/
data class WeatherWithPreferences(
    val weatherList: List<Weather> = emptyList(),
    val showCelsius: Boolean = false,
    val selectedLocationId: String = ""
)
