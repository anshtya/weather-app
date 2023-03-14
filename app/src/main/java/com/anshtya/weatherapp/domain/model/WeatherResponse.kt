package com.anshtya.weatherapp.domain.model

data class WeatherResponse(
    val current: CurrentWeather,
    val location: WeatherLocation
)