package com.anshtya.weatherapp.domain.model

data class WeatherLocation(
    val id: String,
    val country: String,
    val timezoneId: String,
    val name: String,
    val region: String,
)
