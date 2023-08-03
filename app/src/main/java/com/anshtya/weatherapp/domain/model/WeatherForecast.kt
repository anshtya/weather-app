package com.anshtya.weatherapp.domain.model

data class WeatherForecast(
    val id: Long = 0,
    val locationId: String,
    val dateEpoch: Int,
    val astro: Astro,
    val day: Day,
    val hour: List<Hour>
)
