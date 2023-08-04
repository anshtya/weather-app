package com.anshtya.weatherapp.domain.model

data class CurrentWeather(
    val id: Long = 0,
    val locationId: String,
    val weatherType: WeatherType,
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
