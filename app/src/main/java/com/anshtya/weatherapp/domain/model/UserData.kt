package com.anshtya.weatherapp.domain.model

data class UserData(
    val showCelsius: Boolean,
    val apiCallTime: Long,
    val selectedLocation: String,
)