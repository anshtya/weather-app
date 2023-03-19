package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.CurrentWeatherDao
import com.anshtya.weatherapp.data.local.dao.WeatherLocationDao
import com.anshtya.weatherapp.data.local.dto.toModel
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.dto.toEntity
import com.anshtya.weatherapp.domain.model.CurrentWeather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao
): WeatherRepository {
    override suspend fun getWeatherConditions(): CurrentWeather {
        val response = weatherApi.getCurrentWeather()
        val currentWeather = response.current
        val location = response.location
        val id = "${location.name}-${location.region}"

        weatherLocationDao.insertWeatherLocation(location.toEntity(id))
        currentWeatherDao.insertCurrentWeather(currentWeather.toEntity(id))
        return currentWeatherDao.getCurrentWeather().toModel()
    }
}