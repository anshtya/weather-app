package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.data.local.model.WeatherModel

fun WeatherModel.toExternalModel() = Weather(
    weatherLocation = weatherLocation.toExternalModel(),
    currentWeather = currentWeather.toExternalModel(),
    weatherForecast = weatherForecast.map { it.toExternalModel() }
)