package com.anshtya.weatherapp.domain.model

data class Hour(
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val weatherType: WeatherType,
    val isDay: Int,
    val tempC: Double,
    val tempF: Double,
    val time: String,
    val timeEpoch: Int
)
