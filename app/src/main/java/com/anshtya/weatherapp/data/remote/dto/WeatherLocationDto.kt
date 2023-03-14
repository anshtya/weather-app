package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.domain.model.WeatherLocation

data class WeatherLocationDto(
    val country: String,
    val localtime: String,
    val localtime_epoch: Int,
    val name: String,
    val region: String,
    val tz_id: String
) {
    fun toWeatherLocation(): WeatherLocation {
        return WeatherLocation(
            country = country,
            localtime = localtime,
            localtime_epoch = localtime_epoch,
            name = name,
            region = region,
            tz_id = tz_id
        )
    }
}