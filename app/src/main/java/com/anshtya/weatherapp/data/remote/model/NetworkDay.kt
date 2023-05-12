package com.anshtya.weatherapp.data.remote.model

import com.squareup.moshi.Json

data class NetworkDay(
    @field:Json(name = "daily_chance_of_rain")
    val dailyChanceOfRain: Int,

    @field:Json(name = "daily_chance_of_snow")
    val dailyChanceOfSnow: Int,

    @field:Json(name = "maxtemp_c")
    val maxTempC: Double,

    @field:Json(name = "maxtemp_f")
    val maxTempF: Double,

    @field:Json(name = "mintemp_c")
    val minTempC: Double,

    @field:Json(name = "mintemp_f")
    val minTempF: Double,
)