package com.anshtya.weatherapp.domain.model

import com.anshtya.weatherapp.core.model.WeatherCondition

data class Weather(
    val id: String,
    val cloud: Int,
    val condition: WeatherCondition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_mm: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double,
    val country: String,
    val localtime: String,
    val localtime_epoch: Int,
    val name: String,
    val region: String
)
