package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.util.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val isLocationTableNotEmpty: Flow<Boolean>
    suspend fun getLocations(searchQuery: String): List<SearchLocation>
    suspend fun deleteWeatherLocation(locationId: String)
    suspend fun addWeatherLocation(locationUrl: String): Resource<Unit>
}