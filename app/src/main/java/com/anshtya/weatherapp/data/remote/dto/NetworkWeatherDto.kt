package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.data.local.entity.WeatherEntity
import com.anshtya.weatherapp.data.remote.model.NetworkCurrentWeather
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherLocation
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherResponse

fun NetworkWeatherResponse.toEntity(
    id: String,
    currentWeather: NetworkCurrentWeather,
    weatherLocation: NetworkWeatherLocation
): WeatherEntity {
    return WeatherEntity(
        id = id,
        cloud = currentWeather.cloud,
        condition = currentWeather.condition,
        feelslike_c = currentWeather.feelslike_c,
        feelslike_f = currentWeather.feelslike_f,
        humidity = currentWeather.humidity,
        is_day = currentWeather.is_day,
        last_updated = currentWeather.last_updated,
        last_updated_epoch = currentWeather.last_updated_epoch,
        precip_mm = currentWeather.precip_mm,
        temp_c = currentWeather.temp_c,
        temp_f = currentWeather.temp_f,
        uv = currentWeather.uv,
        vis_km = currentWeather.vis_km,
        vis_miles = currentWeather.vis_miles,
        wind_dir = currentWeather.wind_dir,
        wind_kph = currentWeather.wind_kph,
        wind_mph = currentWeather.wind_mph,
        country = weatherLocation.country,
        localtime = weatherLocation.localtime,
        localtime_epoch = weatherLocation.localtime_epoch,
        name = weatherLocation.name,
        region = weatherLocation.region
    )
}