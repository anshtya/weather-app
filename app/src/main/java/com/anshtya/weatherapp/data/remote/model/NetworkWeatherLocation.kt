package com.anshtya.weatherapp.data.remote.model

data class NetworkWeatherLocation(
    val country: String,
    val localtime: String,
    val localtime_epoch: Int,
    val name: String,
    val region: String
)