package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.core.model.CurrentWeather
import com.anshtya.weatherapp.core.model.WeatherForecast

fun CurrentWeatherEntity.toExternalModel() = CurrentWeather(
    locationId = locationId,
    cloud = cloud,
    condition = condition,
    feelsLikeC = feelsLikeC,
    feelsLikeF = feelsLikeF,
    humidity = humidity,
    isDay = isDay,
    tempC = tempC,
    tempF = tempF,
    uv = uv,
    visKm = visKm,
    visMiles = visMiles,
    windDir = windDir,
    windKph = windKph,
    windMph = windMph
)

fun WeatherForecastEntity.toExternalModel() = WeatherForecast(
    locationId = locationId,
    astro =  astro,
    day = day,
    hour = hour
)