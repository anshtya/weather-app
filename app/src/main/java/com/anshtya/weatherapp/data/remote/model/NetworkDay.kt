package com.anshtya.weatherapp.data.remote.model

import com.anshtya.weatherapp.core.model.WeatherCondition
import com.squareup.moshi.Json

data class NetworkDay(
    val condition: WeatherCondition,

    @field:Json(name = "daily_chance_of_rain")
    val daily_chance_of_rain: Int,

    @field:Json(name = "daily_chance_of_snow")
    val daily_chance_of_snow: Int,

    @field:Json(name = "maxtemp_c")
    val maxTempC: Double,

    @field:Json(name = "maxtemp_f")
    val maxTempF: Double,

    @field:Json(name = "mintemp_c")
    val minTempC: Double,

    @field:Json(name = "mintemp_f")
    val minTempF: Double,
)