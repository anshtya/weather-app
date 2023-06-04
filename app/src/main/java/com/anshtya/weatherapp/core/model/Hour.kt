package com.anshtya.weatherapp.core.model

data class Hour(
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val condition: WeatherCondition,
    val isDay: Int,
    val tempC: Double,
    val tempF: Double,
    val time: String
)