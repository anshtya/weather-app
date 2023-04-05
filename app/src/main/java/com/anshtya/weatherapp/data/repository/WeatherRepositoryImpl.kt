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
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun getWeatherCondition(locationId: String): Weather {
        val response = weatherApi.getCurrentWeather(locationId)
        val currentWeather = response.current
        val location = response.location
        val entity = response.toEntity(locationId, currentWeather, location)

        if (weatherDao.getWeatherById(locationId) != null) {
            weatherDao.updateCurrentWeather(entity)
        } else {
            weatherDao.insertCurrentWeather(entity)
        }
        return weatherDao.getWeatherById(locationId)!!.toDomainModel()
    }

    override suspend fun getSavedWeatherCondition(locationId: String): Weather {
        return weatherDao.getWeatherById(locationId)!!.toDomainModel()
    }
}