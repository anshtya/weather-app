package com.anshtya.weatherapp.data.remote.model

import com.squareup.moshi.Json

data class NetworkWeatherLocation(
    val country: String,

    @field:Json(name = "tz_id")
    val timezoneId: String,

    @field:Json(name = "localtime_epoch")
    val localtimeEpoch: Int,

    val name: String,
    val region: String
)