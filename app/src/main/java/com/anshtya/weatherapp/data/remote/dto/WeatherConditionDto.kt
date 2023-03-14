package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.domain.model.WeatherCondition

data class WeatherConditionDto(
//    val code: Int,
    val icon: String,
    val text: String
) {
    fun toWeatherCondition(): WeatherCondition {
        return WeatherCondition(icon, text)
    }
}