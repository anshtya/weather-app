package com.anshtya.weatherapp.data.remote.model.searchLocation

data class SearchLocation(
    val country: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)