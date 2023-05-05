package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun updateWeather()
    fun getWeather(): Flow<List<Weather>>
}