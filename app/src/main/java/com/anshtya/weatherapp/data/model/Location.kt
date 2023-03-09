package com.anshtya.weatherapp.data.model

data class Location(
    val country: String,
    val localtime: String,
    val localtime_epoch: Int,
    val name: String,
    val region: String,
    val tz_id: String
)