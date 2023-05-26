package com.anshtya.weatherapp.data.remote.model

data class NetworkForecastDay(
    val astro: NetworkAstro,
    val day: NetworkDay,
    val hour: List<NetworkHour>
)
