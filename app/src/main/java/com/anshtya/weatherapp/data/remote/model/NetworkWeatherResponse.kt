package com.anshtya.weatherapp.data.remote.model

data class NetworkWeatherResponse(
    val location: NetworkWeatherLocation,
    val current: NetworkCurrentWeather,
    val forecast: NetworkForecast
)