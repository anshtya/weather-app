package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import com.anshtya.weatherapp.domain.model.SavedLocation

fun SavedLocationModel.toExternalModel() = SavedLocation(
    name = name,
    region = region,
    country = country,
    tempC = tempC,
    tempF = tempF,
    maxTempC = maxTempC,
    maxTempF = maxTempF,
    minTempC = minTempC,
    minTempF = minTempF,
    code = code,
    text = text
)