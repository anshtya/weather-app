package com.anshtya.weatherapp.core.model

data class CurrentWeather(
    val id: Long = 0,
    val locationId: String,
    val cloud: Int,
    val condition: WeatherCondition,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val humidity: Int,
    val isDay: Int,
    val tempC: Double,
    val tempF: Double,
    val uv: Double,
    val visKm: Double,
    val visMiles: Double,
    val windDir: String,
    val windKph: Double,
    val windMph: Double
)
