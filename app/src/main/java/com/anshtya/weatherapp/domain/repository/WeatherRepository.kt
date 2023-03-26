package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherConditions(): Weather
}