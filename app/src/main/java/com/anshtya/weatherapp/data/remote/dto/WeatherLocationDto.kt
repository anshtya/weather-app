package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherLocation

fun NetworkWeatherLocation.toEntity(id: String): WeatherLocationEntity {
    return WeatherLocationEntity(
        id = id,
        country = country,
        localtime = localtime,
        localtime_epoch = localtime_epoch,
        name = name,
        region = region,
        tz_id = tz_id
    )
}