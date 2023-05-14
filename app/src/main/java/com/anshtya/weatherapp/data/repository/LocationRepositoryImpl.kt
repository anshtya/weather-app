package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.mapper.toExternalModel
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.mapper.toEntity
import com.anshtya.weatherapp.data.mapper.toSearchLocation
import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : LocationRepository {

    override fun checkIfTableEmpty(): Flow<Boolean> = weatherDao.checkIfTableEmpty().distinctUntilChanged()

    override suspend fun getLocations(searchQuery: String): List<SearchLocation> {
        return weatherApi.searchLocation(searchQuery).map { it.toSearchLocation() }
    }

    override fun getSavedLocations(): Flow<List<SavedLocation>> {
        return weatherDao.getSavedWeatherLocations()
            .map { it.map { savedLocation -> savedLocation.toExternalModel() } }
    }

    override suspend fun deleteWeatherLocation(locationId: String) {
        weatherDao.deleteWeatherLocation(locationId)
    }

    override suspend fun addWeatherLocation(locationUrl: String) {
        val response = weatherApi.getWeatherForecast(locationUrl)
        val location = response.location.toEntity(locationUrl)
        val currentWeather = response.current.toEntity(locationUrl)
        val weatherForecast = response.forecast.forecastDay.first().toEntity(locationUrl)

        if (!weatherDao.checkWeatherExist(locationUrl)) {
            weatherDao.insertWeather(location, currentWeather, weatherForecast)
        }
    }
}