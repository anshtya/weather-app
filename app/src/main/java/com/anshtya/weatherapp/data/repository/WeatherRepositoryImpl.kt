package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.core.model.Weather
import com.anshtya.weatherapp.data.mapper.toExternalModel
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
        weatherDao.getLocationIds().first {
            it.forEach { locationId ->
                val response = weatherApi.getWeatherForecast(locationId)
                val location = response.location
                val currentWeather = response.current
                val forecast = response.forecast.forecastDay.first()
//                weatherDao.updateWeather(currentWeather, forecast)
            }
            true
        }
    }

    override fun getWeather(): Flow<List<Weather>> {
        return weatherDao.getWeather()
            .map { it.map { weatherModel -> weatherModel.toExternalModel() } }
            .distinctUntilChanged()
    }
}