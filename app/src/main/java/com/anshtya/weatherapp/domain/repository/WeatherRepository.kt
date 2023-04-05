package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherCondition(locationId: String): Weather
    suspend fun getSavedWeatherCondition(locationId: String): Weather
}