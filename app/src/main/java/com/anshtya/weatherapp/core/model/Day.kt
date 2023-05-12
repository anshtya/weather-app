package com.anshtya.weatherapp.core.model


data class Day(
    val dailyChanceOfRain: Int,
    val dailyChanceOfSnow: Int,
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
)