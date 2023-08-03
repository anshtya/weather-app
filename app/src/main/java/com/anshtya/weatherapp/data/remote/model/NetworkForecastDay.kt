package com.anshtya.weatherapp.data.remote.model

import com.squareup.moshi.Json

data class NetworkForecastDay(
    @field:Json(name = "date_epoch")
    val dateEpoch: Int,
    val astro: NetworkAstro,
    val day: NetworkDay,
    val hour: List<NetworkHour>
)
