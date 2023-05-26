package com.anshtya.weatherapp.data.remote.model

import com.squareup.moshi.Json

data class NetworkAstro(
    @field:Json(name = "is_moon_up")
    val isMoonUp: Int,

    @field:Json(name = "is_sun_up")
    val isSunUp: Int,

    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)