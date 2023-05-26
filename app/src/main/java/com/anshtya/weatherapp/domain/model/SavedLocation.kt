package com.anshtya.weatherapp.domain.model

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
    val code: Int,
    val text: String
)
