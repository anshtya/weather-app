package com.anshtya.weatherapp.core.model

data class Astro(
    val isMoonUp: Int,
    val isSunUp: Int,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)