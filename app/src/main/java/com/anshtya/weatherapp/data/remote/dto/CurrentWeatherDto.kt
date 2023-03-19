package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.remote.model.NetworkCurrentWeather

fun NetworkCurrentWeather.toEntity(id: String): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        id = id,
        cloud = cloud,
        condition = condition,
        feelslike_c = feelslike_c,
        feelslike_f = feelslike_f,
        humidity = humidity,
        is_day = is_day,
        last_updated = last_updated,
        last_updated_epoch = last_updated_epoch,
        precip_in = precip_in,
        precip_mm = precip_mm,
        temp_c = temp_c,
        temp_f = temp_f,
        uv = uv,
        vis_km = vis_km,
        vis_miles = vis_miles,
        wind_dir = wind_dir,
        wind_kph = wind_kph,
        wind_mph = wind_mph
    )
}