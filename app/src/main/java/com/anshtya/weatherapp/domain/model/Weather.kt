package com.anshtya.weatherapp.domain.model

data class Weather(
    val weatherLocation: WeatherLocation,
    val currentWeather: CurrentWeather,
    val weatherForecast: List<WeatherForecast>
)
