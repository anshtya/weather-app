package com.anshtya.weatherapp.data.repository

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.dto.toDomainModel
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.remote.dto.toEntity
import com.anshtya.weatherapp.data.remote.dto.toSearchLocation
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
            .map { it.map { savedLocation -> savedLocation.toDomainModel() } }
    }

    override suspend fun deleteWeatherLocation(locationId: String) {
        weatherDao.deleteWeatherLocation(locationId)
    }

    override suspend fun addWeatherLocation(locationUrl: String) {
        val response = weatherApi.getCurrentWeather(locationUrl)
        val currentWeather = response.current
        val location = response.location
        val entity = response.toEntity(locationUrl, currentWeather, location)

        if (!weatherDao.checkWeatherExist(locationUrl)) {
            weatherDao.insertCurrentWeather(entity)
        }
    }
}