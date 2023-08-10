package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.model.WeatherType

fun SavedLocationModel.toExternalModel() = SavedLocation(
    id = id,
    country = country,
    name = name,
    region = region,
    weatherType = WeatherType.fromWeatherCondition(code),
    isDay = isDay,
    tempC = tempC,
    tempF = tempF,
    maxTempC = maxTempC,
    maxTempF = maxTempF,
    minTempC = minTempC,
    minTempF = minTempF
)