package com.anshtya.weatherapp.domain.model

data class WeatherLocation(
    val country: String,
    val localtime: String,
    val localtime_epoch: Int,
    val name: String,
    val region: String,
    val tz_id: String
)