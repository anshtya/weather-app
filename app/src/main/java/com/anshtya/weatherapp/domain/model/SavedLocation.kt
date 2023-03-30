package com.anshtya.weatherapp.domain.model

import com.anshtya.weatherapp.core.model.WeatherCondition

data class SavedLocation(
    val condition: WeatherCondition,
    val last_updated: String,
    val temp_c: Double,
    val country: String,
    val name: String,
    val region: String
)
