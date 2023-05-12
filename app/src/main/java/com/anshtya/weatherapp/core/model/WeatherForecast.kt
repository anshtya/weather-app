package com.anshtya.weatherapp.core.model

data class WeatherForecast(
    val id: Long = 0,
    val locationId: String,
    val astro: Astro,
    val day: Day,
    val hour: List<Hour>
)
