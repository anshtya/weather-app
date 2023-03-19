package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.CurrentWeather

interface WeatherRepository {
    suspend fun getWeatherConditions(): CurrentWeather
}