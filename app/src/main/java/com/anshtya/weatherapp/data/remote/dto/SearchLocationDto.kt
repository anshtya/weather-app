package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.data.remote.model.NetworkSearchLocation
import com.anshtya.weatherapp.domain.model.SearchLocation

fun NetworkSearchLocation.toSearchLocation(): SearchLocation {
    return SearchLocation(name, region, country, url)
}