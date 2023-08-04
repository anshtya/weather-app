package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    val weather: Flow<List<Weather>>
    val isLocationTableNotEmpty: Flow<Boolean>
    suspend fun getSearchLocations(searchQuery: String): List<SearchLocation>
    suspend fun addWeather(locationUrl: String): Resource<Unit>
    suspend fun updateWeather()
    suspend fun deleteWeather(locationId: String)
}