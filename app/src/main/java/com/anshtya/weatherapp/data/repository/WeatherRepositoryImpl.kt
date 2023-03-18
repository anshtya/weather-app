package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.WeatherDao
import com.anshtya.weatherapp.data.remote.dto.WeatherResponseDto
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
): WeatherRepository {
    override suspend fun getWeatherConditions(): WeatherResponseDto {
//        return flow {
            val response = weatherApi.getCurrentWeather()
        weatherDao.insertCurrentWeather(response.toWeather())
        return response
//        }
    }
}