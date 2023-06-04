package com.anshtya.weatherapp.domain.model

data class Weather(
    val id: String,
    val name: String,
    val currentWeather: CurrentWeather,
    val weatherForecast: WeatherForecast
)
