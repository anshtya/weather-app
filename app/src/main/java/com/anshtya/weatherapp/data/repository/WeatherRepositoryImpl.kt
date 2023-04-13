package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.dto.toDomainModel
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.dto.toEntity
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun updateWeather(locationId: String): Weather {
        val response = weatherApi.getCurrentWeather(locationId)
        val currentWeather = response.current
        val location = response.location
        val entity = response.toEntity(locationId, currentWeather, location)
        weatherDao.updateCurrentWeather(entity)
        return weatherDao.getWeatherById(locationId)!!.toDomainModel()
    }

    override fun getWeather(): Flow<List<Weather>> {
        return weatherDao.getWeather()
            .map { it.map { weatherEntity -> weatherEntity.toDomainModel() } }
    }
}