package com.anshtya.weatherapp.core.model

/*
* data class for storing list of weather and user settings
*/
data class UserWeather(
    val weatherList: List<Weather> = emptyList(),
    val showCelsius: Boolean = false
)
