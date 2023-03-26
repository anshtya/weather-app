package com.anshtya.weatherapp.domain.repository

import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.model.WeatherLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(searchQuery: String): List<SearchLocation>
    fun savedLocations(): Flow<List<WeatherLocation>>
}