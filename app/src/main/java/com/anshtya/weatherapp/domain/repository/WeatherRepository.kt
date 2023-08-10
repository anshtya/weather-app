package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    val weather: Flow<List<Weather>>

    val savedLocations: Flow<List<SavedLocation>>

    val isLocationTableNotEmpty: Flow<Boolean>

    suspend fun getSearchLocations(searchQuery: String): Resource<List<SearchLocation>>

    suspend fun addWeather(locationUrl: String): Resource<Unit>

    suspend fun updateWeather(): Resource<Unit>

    suspend fun deleteWeather(locationId: String)
}