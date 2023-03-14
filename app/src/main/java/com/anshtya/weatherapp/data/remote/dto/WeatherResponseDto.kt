package com.anshtya.weatherapp.data.remote.dto

import com.anshtya.weatherapp.domain.model.WeatherResponse

data class WeatherResponseDto(
    val current: CurrentWeatherDto,
    val location: WeatherLocationDto
){
    fun toWeatherResponse(): WeatherResponse {
        return WeatherResponse(
            current = current.toCurrentWeather(),
            location = location.toWeatherLocation()
        )
    }
}