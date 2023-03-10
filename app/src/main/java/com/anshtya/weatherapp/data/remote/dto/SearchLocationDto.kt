package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.domain.model.SearchLocation

data class SearchLocationDto(
//    val id: Int,
    val name: String,
    val region: String,
    val country: String,
//    val lat: Double,
//    val lon: Double,
    val url: String
) {
    fun toSearchLocation(): SearchLocation {
        return SearchLocation(name, region, country, url)
    }
}