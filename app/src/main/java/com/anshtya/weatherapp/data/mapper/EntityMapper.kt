package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.domain.model.CurrentWeather
import com.anshtya.weatherapp.domain.model.WeatherForecast
import com.anshtya.weatherapp.domain.model.WeatherType

fun CurrentWeatherEntity.toExternalModel() = CurrentWeather(
    locationId = locationId,
    cloud = cloud,
    weatherType = WeatherType.fromWeatherCondition(condition.code),
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
    hour = hour.map { it.toExternalModel() }
)