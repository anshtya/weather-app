package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.dto.toDomainModel
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.dto.toEntity
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val currentWeatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun getWeatherConditions(): Weather {
        val response = weatherApi.getCurrentWeather()
        val currentWeather = response.current
        val location = response.location
        val id = "${location.name}-${location.region}-${location.country}"

        currentWeatherDao.insertCurrentWeather(response.toEntity(id, currentWeather, location))
        return currentWeatherDao.getCurrentWeather().toDomainModel()
    }
}