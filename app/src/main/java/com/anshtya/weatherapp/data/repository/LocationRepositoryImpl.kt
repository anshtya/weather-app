package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.dao.WeatherLocationDao
import com.anshtya.weatherapp.data.mapper.toEntity
import com.anshtya.weatherapp.data.mapper.toSearchLocation
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.util.Resource
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
) : LocationRepository {
    override val isLocationTableNotEmpty =
        weatherLocationDao.checkTableNotEmpty().distinctUntilChanged()

    override suspend fun getLocations(searchQuery: String): List<SearchLocation> {
        return weatherApi.searchLocation(searchQuery).map { it.toSearchLocation() }
    }

    override suspend fun deleteWeatherLocation(locationId: String) {
        weatherLocationDao.deleteWeatherLocation(locationId)
    }

    override suspend fun addWeatherLocation(locationUrl: String): Resource<Unit> {
        val currentEpochTime = System.currentTimeMillis() / 1000
        val response = weatherApi.getWeatherForecast(locationUrl)
        val location = response.location.toEntity(locationUrl)
        val currentWeather = response.current.toEntity(locationUrl)
        val weatherForecast = response.forecast.forecastDay
            .map {
                it.toEntity(locationUrl, currentEpochTime)
            }

        return if (!weatherDao.checkWeatherExist(locationUrl)) {
            weatherDao.upsertWeather(location, currentWeather, weatherForecast)
            Resource.Success(Unit)
        } else {
            Resource.Error("Location Already Exists")
        }
    }
}