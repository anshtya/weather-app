package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun updateWeather(locationId: String): Weather
    fun getWeather(): Flow<List<Weather>>
}