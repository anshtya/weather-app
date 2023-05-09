package com.anshtya.weatherapp.data.remote.model

data class NetworkWeatherResponse(
    val current: NetworkCurrentWeather,
    val location: NetworkWeatherLocation,
    val forecast: NetworkForecast
)