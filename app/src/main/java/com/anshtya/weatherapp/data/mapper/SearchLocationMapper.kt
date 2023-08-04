package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.data.remote.model.NetworkSearchLocation
import com.anshtya.weatherapp.domain.model.SearchLocation

fun NetworkSearchLocation.toExternalModel() = SearchLocation(
    name = name,
    region = region,
    country = country,
    url = url
)