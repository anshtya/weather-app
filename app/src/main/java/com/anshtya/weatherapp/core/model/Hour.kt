package com.anshtya.weatherapp.core.model

import androidx.room.Embedded

data class Hour(
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    @Embedded val condition: WeatherCondition,
    val isDay: Int,
    val tempC: Double,
    val tempF: Double,
    val time: String
)