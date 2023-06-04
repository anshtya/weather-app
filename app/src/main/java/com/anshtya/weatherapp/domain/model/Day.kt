package com.anshtya.weatherapp.domain.model

data class Day(
    val dailyChanceOfRain: Int,
    val dailyChanceOfSnow: Int,
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
)