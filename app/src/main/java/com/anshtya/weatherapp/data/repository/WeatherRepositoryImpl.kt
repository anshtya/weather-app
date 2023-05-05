package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.dto.toDomainModel
import com.anshtya.weatherapp.data.local.entity.WeatherEntity
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.dto.toEntity
import com.anshtya.weatherapp.core.model.Weather
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun updateWeather() {
        val updatedWeatherList: MutableList<WeatherEntity> = mutableListOf()
        weatherDao.getWeather().first {
            it.forEach { weather ->
                val locationId = weather.id
                val response = weatherApi.getCurrentWeather(locationId)
                val currentWeather = response.current
                val location = response.location
                val entity = response.toEntity(locationId, currentWeather, location)
                updatedWeatherList.add(entity)
            }
            true
        }
        weatherDao.updateCurrentWeather(updatedWeatherList)
    }

    override fun getWeather(): Flow<List<Weather>> {
        return weatherDao.getWeather()
            .map { it.map { weatherEntity -> weatherEntity.toDomainModel() } }
            .distinctUntilChanged()
    }
}