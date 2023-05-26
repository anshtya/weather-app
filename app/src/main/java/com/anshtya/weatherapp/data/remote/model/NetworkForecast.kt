package com.anshtya.weatherapp.data.remote.model

import com.squareup.moshi.Json


data class NetworkForecast(
    @field:Json(name = "forecastday")
    val forecastDay: List<NetworkForecastDay>
)
