package com.anshtya.weatherapp.data.local.model

data class SavedLocationModel(
    val id: String,
    val country: String,
    val name: String,
    val region: String,
    val code: Int,
    val isDay: Int,
    val tempC: Double,
    val tempF: Double,
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double
)