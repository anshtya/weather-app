package com.anshtya.weatherapp.data.local.dto

import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.domain.model.WeatherLocation

fun WeatherLocationEntity.toDomainModel(): WeatherLocation {
    return WeatherLocation(
        id = id,
        country = country,
        localtime = localtime,
        localtime_epoch = localtime_epoch,
        name = name,
        region = region,
        tz_id = tz_id
    )
}