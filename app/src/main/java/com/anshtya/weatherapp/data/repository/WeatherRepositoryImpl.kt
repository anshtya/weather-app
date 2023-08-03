package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.data.local.dao.WeatherLocationDao
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.mapper.toExternalModel
import com.anshtya.weatherapp.data.mapper.toUpdatedModel
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    private val weatherLocationDao: WeatherLocationDao
) : WeatherRepository {
    override suspend fun updateWeather() {
        val currentEpochTime = System.currentTimeMillis() / 1000
        val weatherLocations = weatherLocationDao.getLocationIds().first()

        weatherLocations.forEach { locationId ->
            val response = weatherApi.getWeatherForecast(locationId)
            val location = response.location.toUpdatedModel(locationId)
            val currentWeather = response.current.toUpdatedModel(
                locationId,
                id = weatherDao.getCurrentWeatherId(locationId)
            )
            val weatherForecast = response.forecast.forecastDay

            var index = 0
            val weatherIds = weatherDao.getWeatherForecastIds(locationId)
            val updatedForecast = mutableListOf<WeatherForecastEntity>()
            weatherForecast.forEach {
                val forecast = it.toUpdatedModel(
                    locationId,
                    id = weatherIds[index],
                    currentEpochTime = currentEpochTime
                )
                updatedForecast.add(forecast)
                index++
            }

            weatherDao.upsertWeather(location, currentWeather, updatedForecast)
        }
    }

    override fun getWeather(): Flow<List<Weather>> {
        return weatherDao.getWeather()
            .map { it.map { weatherModel -> weatherModel.toExternalModel() } }
    }
}