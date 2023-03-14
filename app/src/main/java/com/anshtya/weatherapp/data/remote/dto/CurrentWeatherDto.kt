package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.domain.model.CurrentWeather

data class CurrentWeatherDto(
    val cloud: Int,
    val condition: WeatherConditionDto,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
) {
    fun toCurrentWeather(): CurrentWeather {
        return CurrentWeather(
            cloud = cloud,
            condition = condition.toWeatherCondition(),
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
}