package com.anshtya.weatherapp.data.local.model

import com.anshtya.weatherapp.domain.model.WeatherCondition

data class HourModel(
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val condition: WeatherCondition,
    val isDay: Int,
    val tempC: Double,
    val tempF: Double,
    val time: String,
    val timeEpoch: Int
)