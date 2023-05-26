package com.anshtya.weatherapp.core.model

data class UserData(
    val showCelsius: Boolean,
    val apiCallTime: Long,
    val selectedLocation: String,
)