package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.data.remote.dto.WeatherResponseDto

interface WeatherRepository {

    suspend fun getWeatherConditions(): WeatherResponseDto
}