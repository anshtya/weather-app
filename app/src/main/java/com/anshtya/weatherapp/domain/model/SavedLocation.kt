package com.anshtya.weatherapp.domain.model

import com.anshtya.weatherapp.core.model.WeatherCondition

data class SavedLocation(
    val name: String,
    val region: String,
    val country: String,
    val tempC: Double,
    val tempF: Double,
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
    val condition: WeatherCondition,
)
