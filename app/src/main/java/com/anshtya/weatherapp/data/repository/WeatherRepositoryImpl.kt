package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.remote.dto.WeatherResponseDto
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherConditions(): WeatherResponseDto {
//        return flow {
            return weatherApi.getCurrentWeather()
//        }
    }
}