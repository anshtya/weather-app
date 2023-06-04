package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.data.local.model.WeatherModel

fun WeatherModel.toExternalModel() = Weather(
    id = id,
    name = name,
    currentWeather = currentWeather.toExternalModel(),
    weatherForecast = weatherForecast.toExternalModel()
)